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
    private List<String> recipients;
    private String templateName;
    // variables may need rework
    private List<Map<String, String>> variables; // recipient, variableName, variableValue

    public Map<String, Object> getVariablesOfRecipient(String recipient) {
        Optional<Map<String, String>> optional = variables.stream().filter(map -> map.get("recipient").equals(recipient)).findFirst();
        Optional<Map<String, Object>> objectMap =
            optional.map(map -> map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> (Object) e.getValue())));
        return objectMap.orElse(null);
    }
}
