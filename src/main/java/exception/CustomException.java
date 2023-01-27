package exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CustomException extends Throwable {
    private static String exception;

    public CustomException(String exception){
        CustomException.exception = exception;
    }
    public static void showNotice(Exception e) {
        System.out.println(e);
    }

    public static void showNotice(){
        System.out.println(exception);
    }

}
