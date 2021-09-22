package TodoApi;

import com.google.gson.Gson;
import lombok.Data;

@Data
public class Todo {
    private Long id;
    private String summary;
    private String description;

    public Todo(Long id, String summary, String description){
        this.id = id;
        this.summary = summary;
        this.description = description;

    }

    String toJson(){
        Gson gson = new Gson();

        String jsonStr = gson.toJson(this);
        return jsonStr;
    }

}
