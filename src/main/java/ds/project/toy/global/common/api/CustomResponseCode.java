package ds.project.toy.global.common.api;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@JsonFormat(shape = OBJECT)
public enum CustomResponseCode {
    SUCCESS("요청에 성공하였습니다.");
    private final String message;
}
