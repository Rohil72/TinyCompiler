import java.io.FileWriter;
import java.io.IOException;
public class Emitter {
    StringBuilder header;
    StringBuilder code;
    String fullPath; // This can be subject to datatype change
    public Emitter(String fullPath){
        this.fullPath = fullPath;

        this.header = new StringBuilder();
        this.code = new StringBuilder();
    }

    public void emit(String code){
        this.code.append(code);
    }

    public void emitLine(String code){

        this.code.append(code).append("\n");
    }

    public void headerLine(String code){
        this.header.append(code).append("\n");
    }

    public void writeFile(){
        try(FileWriter writer = new FileWriter(fullPath)){
            writer.write(header.toString());
            writer.write(code.toString());

        }
        catch (IOException e){
            System.out.println("Error writing the file " + e.getMessage());

        }
    }
}
