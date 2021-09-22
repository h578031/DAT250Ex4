package TodoApi;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class App {

    static Todo todo = null;
    static List<Todo> todoList;
    public static void main(String[] args) {

        if (args.length > 0) {
            port(Integer.parseInt(args[0]));
        } else {
            port(8080);
        }

        todoList = new ArrayList<Todo>();
        todo = new Todo((long)1, "wash", "wash clothes");
        todoList.add(todo);

        after((req, res) -> {
            res.type("application/json");
        });

        path("/todo", () -> {
            get("", (req, res) -> new Gson().toJson(todoList));
            get("/:id", (req, res) -> {
                String i = req.params(":id");
                long index = Long.parseLong(i);
                return todoList.get(getIndexFromId(index)).toJson();

                    });
            post("", (req, res) -> {
                Todo tD = new Gson().fromJson(req.body(), Todo.class);
                todoList.add(tD);
                return tD;
            });
            put("/:id", (req, res) -> {
                Todo temp = new Gson().fromJson(req.body(), Todo.class);
                int index = getIndexFromId(temp.getId());
                if(index != -1){
                    todoList.set(index, temp);
                }
                else {
                    res.status(400);
                }

                return temp.toJson();
                });
            delete("/:id", (req, res) -> {
                String i = req.params(":id");
                long index = Long.parseLong(i);
                return todoList.remove(getIndexFromId(index)).toJson();
                });
            });

        }



        public static int getIndexFromId(Long id){
            int i=0;
            int res = -1;
            while(res==-1 && i<todoList.size()){
                if(todoList.get(i).getId() == id){
                    res = i;
                }
                else{
                    i++;
                }
            }
            return res;
        }
}
