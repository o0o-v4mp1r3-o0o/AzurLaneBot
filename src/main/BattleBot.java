package main;

import sun.applet.Main;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BattleBot extends main{

    public BattleBot() throws AWTException {
    }
    public static Integer wtf = 0;
    public static Integer wtf1 = 0;
    public static Integer escortmission = 0;
    public static Integer fierceassault = 0;
    public static Integer advancemission = 0;


    private static void commissions() throws IOException, InterruptedException {
        main.ImageLooper("commissions", .9);
        main.robot.mouseMove(main.robox, main.roboy);
        main.PressMouse();
        main.finder = 0;
        while (finder < 1) {
            ImageLooper("comcomplete", .8,3.0);
            if (main.finder > 0) {
                main.FindMultipleImage("comcomplete", .8);
                main.robot.mouseMove(main.robox, main.roboy);
                main.PressMouse();
                Thread.sleep(2000);
                main.robot.mouseMove(main.robox-200, main.roboy);
                PressMouse();
                Thread.sleep(3000);
                PressMouse();
                finder = 0;
                wtf++;
                continue;
            } else {
                finder = 1;
            }
        }
        finder = 0;
        ImageLooper("emptycommission",.85);
        for (int i = 0; i < wtf; i++) {
            SearchImageUnless("emptycommission","specialcommission",382,14);
            if(gems.size() > 0){
                robox = gems.get(0);
                roboy = gems.get(1);
                robot.mouseMove(robox,roboy);
                PressMouse();
                LoopAndClick("addtrainee",.85,2.0);
                FindTrainees("southdakota");
                FindTrainees("taihou");
                FindTrainees("nevada");
                FindTrainees("duke");
                FindTrainees("heinrich");
                FindTrainees("ark");
                LoopAndClick("confirmbutton",.85,3.0);
                LoopAndClick("commissionready",.9,3.0);
                LoopAndClick("confirmbutton",.85,3.0);
                robot.mouseMove(robox,roboy+250);
                PressMouse();
                gems.clear();
                finder = 0;
                continue;
            }

            SearchImageUnless("emptycommission","commissioncube",966,-45);
            for(int c = 0; c < (gems.size()/2); c+=2){
                robox = gems.get(c);
                roboy = gems.get(c+1);
                robot.mouseMove(main.robox, main.roboy);
                PressMouse();
                ImageLooper("commissionrecommend",.9);
                robot.mouseMove(robox,roboy);
                PressMouse();
                ImageLooper("commissionready", .9);
                robot.mouseMove(robox,roboy);
                PressMouse();
                robot.mouseMove(robox,roboy+250);
                PressMouse();
                finder = 0;
                i++;
            }
            finder = 0;
            FindMultipleImage("emptycommission", .8);
            if (finder > 0) {
                main.robot.mouseMove(main.robox, main.roboy);
                main.PressMouse();
                ImageLooper("commissionrecommend",.9);
                robot.mouseMove(robox,roboy);
                PressMouse();
                ImageLooper("commissionready", .9);
                robot.mouseMove(robox,roboy);
                PressMouse();
                robot.mouseMove(robox,roboy+250);
                PressMouse();
                finder = 0;
            } else {
                robot.mouseWheel(3);
                FindMultipleImage("emptycommission", .8);
                robot.mouseMove(main.robox, main.roboy);
                PressMouse();
                ImageLooper("commissionrecommend",.9);
                robot.mouseMove(robox,roboy);
                PressMouse();
                ImageLooper("commissionready", .9);
                robot.mouseMove(robox,roboy);
                PressMouse();
                robot.mouseMove(robox,roboy+250);
                PressMouse();
                finder = 0;
            }
        }
        ImageLooper("back",.8);
        robot.mouseMove(robox,roboy);
        PressMouse();
    }
    private static void MissionLooper() throws IOException, InterruptedException {
        finder = 0;
        while(true){
            System.out.println("not breaking");
            FindMultipleImage("escortmission",.85);
            if(finder > 0 && escortmission != 2){
                robot.mouseMove(robox,roboy);
                PressMouse();
                escortmission = 1;
                break;
            }
            FindMultipleImage("escortmission1",.85); //escortmission1
            if(finder > 0 && escortmission != 2){
                robot.mouseMove(robox,roboy);
                PressMouse();
                ImageLooper("escortmission",.85);
                if(finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                }
                escortmission = 1;
                break;
            }
            FindMultipleImage("escortmission2",.85); //escortmission2
            if(finder > 0 && escortmission != 2){
                robot.mouseMove(robox,roboy);
                PressMouse();
                ImageLooper("escortmission",.85);
                if(finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                }
                escortmission = 1;
                break;
            }
            FindMultipleImage("advancemission",.85);
            if(finder > 0 && advancemission != 2){
                robot.mouseMove(robox,roboy);
                PressMouse();
                advancemission = 1;
                break;
            }
            FindMultipleImage("advancemission1",.85);
            if(finder > 0 && advancemission != 2){
                robot.mouseMove(robox,roboy);
                PressMouse();
                ImageLooper("advancemission",.85);
                if(finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                }
                advancemission = 1;
                break;
            }
            FindMultipleImage("advancemission2",.85);
            if(finder > 0 && advancemission != 2){
                robot.mouseMove(robox,roboy);
                PressMouse();
                ImageLooper("advancemission",.85);
                if(finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                }
                advancemission = 1;
                break;
            }
            FindMultipleImage("fierceassault",.85);
            if(finder > 0 && fierceassault != 2){
                robot.mouseMove(robox,roboy);
                PressMouse();
                fierceassault = 1;
                break;
            }
            FindMultipleImage("fierceassault1",.85);
            if(finder > 0 && fierceassault != 2){
                robot.mouseMove(robox,roboy);
                PressMouse();
                ImageLooper("fierceassault",.85);
                if(finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                }
                fierceassault = 1;
                break;
            }
            FindMultipleImage("fierceassault2",.85);
            if(finder > 0 && fierceassault != 2){
                robot.mouseMove(robox,roboy);
                PressMouse();
                ImageLooper("fierceassault",.85);
                if(finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                }
                fierceassault = 1;
                break;
            }
            if(finder.equals(0)){
                break;
            }
        }
    }
    private static void raidbattler(String s) throws IOException, InterruptedException {
        for(int i = 0; i < 3; i++) {
            ImageLooper(s, .9);
            FindMultipleImage(s, .9);
            robot.mouseMove(robox, roboy);
            PressMouse();
            Thread.sleep(2000);
            ImageLooper("raidbattle",.85,3.0);
            robot.mouseMove(robox, roboy);
            PressMouse();
            finder = 0;
            ImageLooper("battleconfirm", .85);
            FindMultipleImage("battleconfirm", .85);
            robot.mouseMove(robox, roboy);
            PressMouse();
            finder = 0;
            ImageLooper("endraid", .8);
            robot.mouseMove(robox, roboy);
            PressMouse();
            Thread.sleep(2000);
            finder = 0;
            FindMultipleImage("taptocontinue", .8);
            if (finder > 0) {
                robot.mouseMove(robox, roboy);
                PressMouse();
                Thread.sleep(2000);
                finder = 0;
            }
            ImageLooper("confirmbattle", .85);
            robot.mouseMove(robox, roboy);
            PressMouse();
        }
    }
    private static void dailyraids() throws IOException, InterruptedException {
        ImageLooper("dailyraids",.85);
        robot.mouseMove(robox,roboy);
        PressMouse();
        finder = 0;
            ImageLooper("tacticaltraining", .9);
            if (finder > 0) {
                System.out.println("searching");
                robot.mouseMove(robox, roboy);
                PressMouse();
                Thread.sleep(2000);
                finder = 0;
                for(int i = 0; i < 3; i++) {
                ImageLooper("aviation", .9);
                FindMultipleImage("aviation", .9);
                robot.mouseMove(robox, roboy);
                PressMouse();
                Thread.sleep(2000);
                ImageLooper("raidbattle",.85,3.0);
                robot.mouseMove(robox, roboy);
                PressMouse();
                finder = 0;
                ImageLooper("raidgotit",.85,2.0);
                if(finder > 0){
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                    finder = 0;
                }
                ImageLooper("nonauto",.85,2.0);
                    if(finder > 0){
                        robot.mouseMove(robox, roboy);
                        PressMouse();
                        finder = 0;
                    }
                ImageLooper("battleconfirm", .85,2.0);
                    if(finder.equals(0)){
                        break;
                    }
                ImageLooper("battleconfirm", .85,2.0);
                robot.mouseMove(robox, roboy);
                PressMouse();
                finder = 0;
                ImageLooper("endraid", .85,20.0);
                FindMultipleImage("endraid", .85);
                robot.mouseMove(robox, roboy);
                PressMouse();
                Thread.sleep(2000);
                finder = 0;
                FindMultipleImage("taptocontinue", .8);
                if (finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                    Thread.sleep(2000);
                    finder = 0;
                }
                ImageLooper("confirmbattle", .85,20.0);
                robot.mouseMove(robox, roboy);
                PressMouse();
            }
                ImageLooper("back",.85);
                robot.mouseMove(robox, roboy);
                PressMouse();
        }

        for(int i = 0; i < 3; i++) {
            MissionLooper();
            if (escortmission.equals(1)) {
                escortmission = 2;
                ImageLooper("escortmissiongo", .9, 30.0);
                raidbattler("escortmissiongo");
            }
            if (fierceassault.equals(1)) {
                fierceassault = 2;
                ImageLooper("fierceassaultgo", .9, 30.0);
                raidbattler("fierceassaultgo");
            }
            if (advancemission.equals(1)) {
                advancemission = 2;
                ImageLooper("advancemissiongo", .9, 30.0);
                raidbattler("advancemissiongo");
            }
            if(escortmission < 1 && fierceassault < 1 && advancemission < 1){
                break;
            }
        }
        ImageLooper("back",.85);
        robot.mouseMove(robox, roboy);
        PressMouse();
        Thread.sleep(2000);
        ImageLooper("back",.85);
        robot.mouseMove(robox, roboy);
        PressMouse();
        Thread.sleep(2000);
    }
    private static void MainCampaign() throws IOException, InterruptedException {
        ImageLooper("maincampaign",.85);
        robot.mouseMove(robox, roboy);
        PressMouse();
        ImageLooper("hardmode",.85,2.0);
        if(finder > 0){
            robot.mouseMove(robox, roboy);
            PressMouse();
        }
        ImageLooper("hardmodemap",.85);
        robot.mouseMove(robox, roboy);
        PressMouse();
        ImageLooper("hardmodemapgo",.85);
        robot.mouseMove(robox, roboy);
        PressMouse();
        ImageLooper("hardmodemapgo2",.85);
        robot.mouseMove(robox, roboy);
        PressMouse();
        for(int i = 0; i < 3; i++) {
            ImageLooper("continuehardmode", .85);
            robot.mouseMove(robox, roboy);
            PressMouse();
        }
        ImageLooper("back",.85);
        robot.mouseMove(robox, roboy);
        PressMouse();
        Thread.sleep(2000);
        ImageLooper("back",.85);
        robot.mouseMove(robox, roboy);
        PressMouse();
        Thread.sleep(2000);
    }
    private static void CollectRewards() throws IOException, InterruptedException {
        ImageLooper("missions",.85,1.5);
        robot.mouseMove(robox, roboy);
        PressMouse();
        ImageLooper("collectmissionrewards",.85,2.0);
        while(finder > 0) {
            ImageLooper("collectmissionrewards", .85, 2.0);
            if (finder > 0) {
                robot.mouseMove(robox, roboy);
                PressMouse();
                ImageLooper("taptocontinue", .85, 3.0);
                robot.mouseMove(robox, roboy);
                PressMouse();
                continue;
            }
                ImageLooper("missionclaim", .85, 2.0);
                if (finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                    ImageLooper("taptocontinue", .85, 3.0);
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                    continue;
            }
                ImageLooper("back",.85,3.0);
                if(finder < 1){
                    PressMouse();
                    ImageLooper("taptocontinue", .85, 3.0);
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                    continue;
                }else{
                    break;
                }
        }
        ImageLooper("back",.85,3.0);
        robot.mouseMove(robox, roboy);
        PressMouse();
        //NEEDS A CONDITIONAL FOR NEW SHIP ACQUIRED
    }
    private static void FindTrainees(String s) throws IOException, InterruptedException {
        while(true) {
            ImageLooper(s, .85, 0.7);
            if (finder < 1) {
                robot.mouseWheel(1);
                continue;
            }else{
                robot.mouseMove(robox,roboy);
                PressMouse();
                break;
            }
        }
    }
    public static void battle() throws IOException, InterruptedException {
        //main.FindMultipleImage("comcomplete",.8);
        main.ImageLooper("battle",.9,3.0);
        main.robot.mouseMove(main.robox,main.roboy);
        main.PressMouse();
        main.finder=0;
        commissions();
        dailyraids();
        MainCampaign();
        CollectRewards();
        OperationSiren.OpSci(null);
    }
}
