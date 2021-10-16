package main;

import java.awt.*;
import java.io.IOException;

public class OperationSiren extends main{

    public OperationSiren() throws AWTException {
    }

    private static void SetUpTeam() throws IOException, InterruptedException {
        LoopAndClick("sirenadd",.9,2.0);
        LoopAndClick("centaur",.9,2.0);
        LoopAndClick("confirmbutton",.9,2.0);
        LoopAndClick("addmaintop",.9,2.0);
        LoopAndClick("shinano",.9,2.0);
        LoopAndClick("confirmbutton",.9,2.0);
        LoopAndClick("addmainbottom",.9,2.0);
        LoopAndClick("enterpriseusan",.9,2.0);
        LoopAndClick("confirmbutton",.9,2.0);
        LoopAndClick("addvanguardleft",.9,2.0);
        LoopAndClick("noshiro",.9,2.0);
        LoopAndClick("confirmbutton",.9,2.0);
        LoopAndClick("addvanguardmiddle",.9,2.0);
        LoopAndClick("helena",.9,2.0);
        LoopAndClick("confirmbutton",.9,2.0);
        LoopAndClick("addvanguardright",.9,2.0);
        LoopAndClick("stlouis",.9,2.0);
        LoopAndClick("confirmbutton",.9,2.0);

    }

    private static void DefeatThree() throws IOException, InterruptedException {
        LoopAndClick("operationsiren",.9,3.0);
        System.out.println("siren clicked");
        LoopAndClick("sirenoverview",.85,3.0);
        LoopAndClick("showdown",.85,3.0);
        ImageLooper("sirenbosslist",.9,3.0);
        if(finder > 0){
            robot.mouseMove(robox,roboy);
            PressMouse();
        }
        ImageLooper("mybosses",.85,3.0);
        if(finder > 0){
            LoopAndClick("sirenstartbattle",.9,3.0);
            ImageLooper("sirenadd",.9,2.0);
            if(finder > 0){
                System.out.println("YER FUCKED");
                SetUpTeam();
            }
            LoopAndClick("battleconfirm",.9,2.0);
            ImageLooper("confirmbutton",.9,1.5);
            if(finder > 0){
                robot.mouseMove(robox,roboy);
                PressMouse();
            }
            LoopAndClick("taptocontinue",.8,1000.0);
            LoopAndClick("sirenconfirm",.55,3.0);
            for(int i = 0; i < 2; i++){
                ImageLooper("sirenbosslist",.9,3.0);
                if(finder > 0){
                    robot.mouseMove(robox,roboy);
                    PressMouse();
                }
                LoopAndClick("sirenstartbattle",.9,3.0);
                LoopAndClick("battleconfirm",.9,2.0);
                LoopAndClick("taptocontinue",.8,1000.0);
                ImageLooper("confirmbutton",.9,1.5);
                if(finder > 0){
                    robot.mouseMove(robox,roboy);
                    PressMouse();
                }
                LoopAndClick("sirenconfirm",.55,3.0);
            }
        }
    }

    public static void OpSci(String[] args) throws IOException, InterruptedException {
        LoopAndClick("battle",.9,3.0);
        System.out.println("wtf");
        finder=0;
        DefeatThree();
    }
}
