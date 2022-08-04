package views;

import javafx.fxml.FXMLLoader;
import models.Card;
import java.io.IOException;

public class UserCellData {
    public UserCellData() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/user-cell.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void setInfo(Card card)
    {

    }
}
