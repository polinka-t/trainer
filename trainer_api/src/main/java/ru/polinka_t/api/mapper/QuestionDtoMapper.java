package ru.polinka_t.api.mapper;

import org.mapstruct.Mapper;
import ru.polinka_t.api.dto.OpenQuestionCardDto;
import ru.polinka_t.domain.model.OpenQuestionCard;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionDtoMapper {
    OpenQuestionCard mapToModel(OpenQuestionCardDto dto);
    OpenQuestionCardDto mapToDto(OpenQuestionCard question);
    List<OpenQuestionCard> mapToModel(List<OpenQuestionCardDto> questionDtos);
    List<OpenQuestionCardDto> mapToDto(List<OpenQuestionCard> questions);
}
