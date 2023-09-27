package com.omerfurkan.studentforum.requests;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EmailRequest {

    private String subject;
    private String body;
    private String recipient;
    private String templateName;
    // variables may need rework
    private Map<String, String> variables; //  {variableName} -> variableValue

    public Map<String, Object> getObjectVariables() {
        return variables.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> (Object) e.getValue()));
    }

}
