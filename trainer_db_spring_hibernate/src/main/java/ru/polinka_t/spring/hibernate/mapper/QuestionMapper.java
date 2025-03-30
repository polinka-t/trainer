package ru.polinka_t.spring.hibernate.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.polinka_t.domain.model.OpenQuestionCard;
import ru.polinka_t.spring.hibernate.entity.OpenQuestionCardEntity;

import java.util.List;

@Mapper(componentModel = "ru/polinka_t/spring")
public interface QuestionMapper {
    @Mapping(target = "deadLine", ignore = true)
    OpenQuestionCard mapToModel(OpenQuestionCardEntity entity);
    OpenQuestionCardEntity mapToEntity(OpenQuestionCard questionCards);
    List<OpenQuestionCard> mapToModel(List<OpenQuestionCardEntity> entities);
    List<OpenQuestionCardEntity> mapToEntity(List<OpenQuestionCard> questionCards);
}