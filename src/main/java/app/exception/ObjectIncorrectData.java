package app.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;


@Component
@Scope("prototype")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectIncorrectData {

    private String info;

    public ObjectIncorrectData getInstanceWithInfo(String info) {
        return new ObjectIncorrectData(info);
    }
}
