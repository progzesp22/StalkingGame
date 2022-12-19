package com.progzesp.stalking.service.impl;

import com.progzesp.stalking.domain.answer.AnswerEto;
import com.progzesp.stalking.domain.answer.ModifyAnswerEto;
import com.progzesp.stalking.domain.answer.NoNavPosEto;
import com.progzesp.stalking.domain.mapper.AnswerMapper;
import com.progzesp.stalking.persistance.entity.*;
import com.progzesp.stalking.persistance.entity.answer.*;
import com.progzesp.stalking.persistance.repo.AnswerRepo;
import com.progzesp.stalking.persistance.repo.GameRepo;
import com.progzesp.stalking.persistance.repo.TaskRepo;
import com.progzesp.stalking.persistance.repo.UserRepo;
import com.progzesp.stalking.service.AnswerService;
import com.progzesp.stalking.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private AnswerRepo answerRepository;

    @Autowired
    private TaskRepo taskRepository;

    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private GameService gameService;

    @Override
    public Pair<Integer, AnswerEto> save(AnswerEto newAnswer, Principal user) {
        clearEto(newAnswer);
        AnswerEntity answerEntity = answerMapper.mapToEntity(newAnswer);

        if (!answerEntity.validate())
            return null;

        UserEntity userEntity = userRepo.getByUsername(user.getName());
        answerEntity.setUser(userEntity);

        // bind task with answer
        Optional<TaskEntity> optionalTask = taskRepository.findById(newAnswer.getTaskId());
        TaskEntity taskEntity = optionalTask.orElse(null);
        if(taskEntity == null) {
            return Pair.of(400, answerMapper.mapToETO(answerEntity));
        }
        answerEntity.setTask(taskEntity);

        // bind Game with answer
        Optional<GameEntity> optionalGame = gameRepo.findById(taskEntity.getGameId());
        GameEntity gameEntity = optionalGame.orElse(null);
        if(gameEntity == null) {
            return Pair.of(400, answerMapper.mapToETO(answerEntity));
        }
        answerEntity.setGame(gameEntity);

        // REMOVED FROM PROTO AND COLLIDES WITH MAIN WORKFLOW - UNCOMMENT AFTER FIXED AND NEEDED
        // // check if all prerequisite tasks are approved
        // for(TaskEntity prerequisiteTask : taskEntity.getPrerequisiteTasks()) {
        //     AnswerEntity res = null;

        //     AnswerEntity[] entities = new AnswerEntity[] {new TextEntity(), new QREntity(),
        //             new PhotoEntity(), new AudioEntity(), new NavPosEntity()};
        //     for (AnswerEntity toFind : entities) {
        //         toFind.setTask(prerequisiteTask);
        //         toFind.setChecked(true);
        //         toFind.setApproved(true);
        //        List<AnswerEntity> list = answerRepository.findAll(Example.of(toFind));
        //        for(AnswerEntity entity : list) {
        //            if(Objects.equals(entity.getUser().getTeamId(), userEntity.getTeamId())) {
        //                res = list.get(0);
        //            }
        //        }
        //    }

        //     if(res == null) {
        //         return Pair.of(403, answerMapper.mapToETO(answerEntity));
        //     }
        // }

        answerEntity = this.answerRepository.save(answerEntity);
        return Pair.of(200, answerMapper.mapToETO(answerEntity));
    }


    @Override
    public Pair<Integer, ModifyAnswerEto> modifyAnswer(Long id, ModifyAnswerEto answerEto, Principal user) {

        Optional<AnswerEntity> foundEntity = answerRepository.findById(id);
        if (foundEntity.isPresent()) {
            AnswerEntity answerEntity = foundEntity.get();

            UserEntity userEntity = userRepo.getByUsername(user.getName());

            if(!Objects.equals(answerEntity.getGame().getGameMasterId(), user.getName())) {
                return Pair.of(403, new ModifyAnswerEto());
            }

            answerEntity.setChecked(answerEto.isChecked());
            answerEntity.setApproved(answerEto.isApproved());
            answerEntity.setScore(answerEto.getScore());

            TeamEntity team = null;

            for (TeamEntity teamEntity : answerEntity.getGame().getTeams()) {
                for (TeamEntity userTeam : answerEntity.getUser().getTeams()) {
                    if(Objects.equals(userTeam.getId(), teamEntity.getId())) {
                        team = teamEntity;
                        break;
                    }
                }
            }

            if(team == null) {
                return Pair.of(400, new ModifyAnswerEto());
            }

            if(answerEntity.getGame().getAnswerEntityList() != null) {
                if (answerEntity.getGame().getEndCondition().equals(EndCondition.SCORE)) {
                    Integer score = 0;
                    for (AnswerEntity answerEntityIterate : answerEntity.getGame().getAnswerEntityList()) {
                        if (answerEntityIterate.isChecked() && answerEntityIterate.isApproved()) {
                            for (TeamEntity userTeam : answerEntityIterate.getUser().getTeams()) {
                                if(Objects.equals(userTeam.getId(), team.getId())) {
                                    score += answerEntityIterate.getScore();
                                    break;
                                }
                            }
                        }
                    }
                    if (score >= answerEntity.getGame().getEndScore()) {
                        gameService.endGameplay(answerEntity.getGameId());
                    }
                } else if (answerEntity.getGame().getEndCondition().equals(EndCondition.TASKS)) {
                    boolean areAllDone = true;
                    for (TaskEntity taskEntity : answerEntity.getGame().getTaskEntityList()) {
                        if (Objects.equals(taskEntity.getId(), answerEntity.getTaskId())) continue;
                        boolean isTaskDone = false;
                        AnswerEntity[] entities = new AnswerEntity[] {new TextEntity(), new QREntity(),
                                new PhotoEntity(), new AudioEntity(), new NavPosEntity()};
                        for (AnswerEntity toFind : entities) {
                            toFind.setTask(taskEntity);
                            toFind.setChecked(true);
                            toFind.setApproved(true);

                            List<AnswerEntity> list = answerRepository.findAll(Example.of(toFind));
                            for(AnswerEntity entity : list) {
                                for (TeamEntity userTeam : entity.getUser().getTeams()) {
                                    if(Objects.equals(userTeam.getId(), team.getId())) {
                                        isTaskDone = true;
                                        break;
                                    }
                                }
                            }
                            if(isTaskDone) break;
                        }
                        if (!isTaskDone) {
                            areAllDone = false;
                            break;
                        }
                    }
                    if(areAllDone) {
                        gameService.endGameplay(answerEntity.getGameId());
                    }
                }
            }

            return Pair.of(200, answerMapper.mapToModifyAnswerETO(answerEntity));
        }
        else
            return Pair.of(400, new ModifyAnswerEto());
    }

    @Override
    public boolean deleteAnswer(Long id) {

        Optional<AnswerEntity> answerOptional = answerRepository.findById(id);
        if (answerOptional.isEmpty()) {
            return false;
        }
        else {
            answerRepository.delete(answerOptional.get());
            return answerRepository.findById(id).isEmpty();
        }
    }

    @Override
    public Pair<Integer, AnswerEto> findAnswerById(Long id, Principal user) {
        final Optional<AnswerEntity> result = this.answerRepository.findById(id);
        if(result.isPresent()) {
            AnswerEntity answerEntity = result.get();
            Long userId = userRepo.getByUsername(user.getName()).getId();
            if(!Objects.equals(answerEntity.getUserId(), userId) && !Objects.equals(answerEntity.getGame().getGameMasterId(), user.getName())) {
                return Pair.of(403, new NoNavPosEto());
            }
            return Pair.of(200, answerMapper.mapToETO(answerEntity));
        }
        return Pair.of(400, new NoNavPosEto());
    }

    @Override
    public Pair<Integer, List<AnswerEto>> findAnswersByCriteria(Optional<Long> gameId, Optional<String> filter, Principal user) {
        List<AnswerEntity> result;
        if (filter.isPresent()) {
            if (filter.get().equals("checked")) {
                result = this.answerRepository.findAnswersByCriteria(gameId, Optional.of(true));
            }
            else if (filter.get().equals("unchecked")) {
                result = this.answerRepository.findAnswersByCriteria(gameId, Optional.of(false));
            }
            else {
                result = this.answerRepository.findAnswersByCriteria(gameId, Optional.empty());
            }
        }
        else {
            result = this.answerRepository.findAnswersByCriteria(gameId, Optional.empty());
        }
        return Pair.of(200, answerMapper.mapToETOList(result));
    }

    private void clearEto(AnswerEto eto) {
        eto.setApproved(false);
        eto.setChecked(false);
        eto.setScore(0);
    }

}
