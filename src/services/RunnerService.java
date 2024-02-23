package services;

import controllers.UserController;
import domain.user.User;
import enums.UserOption;
import exceptions.DbConnectionException;
import exceptions.UserException;
import lombok.extern.log4j.Log4j2;

import java.util.InputMismatchException;

@Log4j2
public final class RunnerService {

    public static void runProgram() {

        //Todo - Testar criação de usuário e exclusão e fazer lógica de Login

        UserController userController = new UserController();
        UserOption userOption = null;

        do {
            try {
                userOption = ReaderService.readEnum(UserOption.values());
                userOption = UserOption.CREATE_USER;


                switch (userOption) {
                    case LOGIN -> {
                        //Call login logic
                    }
                    case CREATE_USER -> {
                        User user = userController.create();
                        //Call login logic
                    }
                    case DELETE_USER -> {
                        userController.delete();
                    }
                }

            } catch (UserException | DbConnectionException e) {
                log.error(e.getMessage());
            } catch (InputMismatchException e) {
                log.error("Invalid input, stopping the program..");
                userOption = UserOption.OUT;
            }

        } while (userOption != UserOption.OUT);

    }
}
