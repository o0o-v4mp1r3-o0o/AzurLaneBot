package main;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

public class main {
    static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public main() throws AWTException {
    }

    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static Rectangle area = new Rectangle(0, 0, screenSize.width, screenSize.height);
    public static BufferedImage screenshot = robot.createScreenCapture(area);
    public static Mat mate;
    public static Integer finder = 0;
    public static Integer robox = 0;
    public static Integer roboy = 0;
    public static double halfsecond = 0;
    public static ArrayList<Integer> gems = new ArrayList<Integer>();
    public static ArrayList<Integer> list = new ArrayList<Integer>();
    public static ArrayList<Integer> comparer = new ArrayList<Integer>();

    static {
        try {
            mate = BufferedImage2Mat(screenshot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void MouseGlider(Integer s, int repeat, Integer IncrementPixel) throws InterruptedException {
        for(int i = 0; i < repeat; i++) {
            robot.mouseMove(robox + s, roboy);
            s += IncrementPixel;
            Thread.sleep(500);
        }
    }
    public static void WaitUntilImageGone(String imagefile, Double threshold, Double TimeLimit) throws IOException,
            InterruptedException {
        finder = 0;
        Double time = 0.0;
        while(true){
            UpdatePicture();
            mate = BufferedImage2Mat(screenshot);
            FindMultipleImage(imagefile,threshold);
            if(finder > 0){
                finder = 0;
            }else{
                System.out.println("success");
                break;
            }
            if(TimeLimit > 0){
                time+=.5;
                if(time > TimeLimit){
                    break;
                }
            }
            Thread.sleep(500);
        }
    }
    public static void ImageLooper(String imagefile, Double threshold) throws IOException, InterruptedException {
        finder = 0;
        while(finder.equals(0)){
            UpdatePicture();
            mate = BufferedImage2Mat(screenshot);
            FindMultipleImage(imagefile,threshold);
            Thread.sleep(500);
        }
    }
    public static void ImageLooper(String imagefile, Double threshold, Double wholesecondstimer) throws IOException,
            InterruptedException {
        finder = 0;
        while(finder.equals(0)){
            UpdatePicture();
            mate = BufferedImage2Mat(screenshot);
            FindMultipleImage(imagefile,threshold);
            halfsecond+=.5;
            if(halfsecond > wholesecondstimer){
                break;
            }
            Thread.sleep(500);
        }
        halfsecond=0;
    }
    public static void LoopAndClick(String imagefile, Double i, Double ii) throws IOException, InterruptedException {
        ImageLooper(imagefile,i,ii);
        robot.mouseMove(robox,roboy);
        PressMouse();
    }
    public static void LoopAndClickUntilGone(String imagefile, Double i, Double ii, Double WaitUntilLimit) throws IOException,
            InterruptedException {
        ImageLooper(imagefile,i,ii);
        robot.mouseMove(robox,roboy);
        PressMouse();
        WaitUntilImageGone(imagefile,i,WaitUntilLimit);
    }
    private static void FindOneImage(String find, double thresholdd) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Imgcodecs imageCodecs = new Imgcodecs();
        mate = BufferedImage2Mat(screenshot);
        Mat cube = imageCodecs.imread("C:\\Users\\madse\\IdeaProjects\\ColorBot\\Resources\\images\\" + find + ".png");
        Mat outputImage=new Mat();
        int machMethod= Imgproc.TM_CCOEFF_NORMED;
        //Template matching method
        Imgproc.matchTemplate(mate, cube, outputImage, machMethod);
        Core.MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        org.opencv.core.Point matchLoc=mmr.maxLoc;
        double threshold = thresholdd;
        double maxval;
        maxval = mmr.maxVal;
        //Draw rectangle on result image
        if(maxval >= threshold) {
            robox = (int) (matchLoc.x + cube.width()/2);
            roboy = (int) (matchLoc.y + cube.height()/2);
        }
        Imgcodecs.imwrite("C:\\Users\\madse\\IdeaProjects\\ColorBot\\Resources\\images\\sonuc.png", mate);

    }
    public static void FindMultipleImage(String find, double thresholdd) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        finder=0;
        Imgcodecs imageCodecs = new Imgcodecs();
        mate = BufferedImage2Mat(screenshot);
        Mat tech2 = imageCodecs.imread("C:\\Users\\madse\\IdeaProjects\\ColorBot\\Resources\\images\\" + find +
                ".png");
        Mat outputImage=new Mat();
        int machMethod= Imgproc.TM_CCOEFF_NORMED;
        Imgproc.matchTemplate(mate, tech2, outputImage, machMethod);
        Imgproc.threshold(outputImage, outputImage, 0.1, 1, Imgproc.THRESH_TOZERO);
        double threshold = thresholdd;
        double maxval;
        Mat dst = null;

        while(true)
        {
            if(finder > 1000){
                break;
            }
            Core.MinMaxLocResult maxr = Core.minMaxLoc(outputImage);
            Point maxp = maxr.maxLoc;
            maxval = maxr.maxVal;
                System.out.println(maxval);
            robox = (int) (maxp.x + tech2.width()/2);
            roboy = (int) (maxp.y + tech2.height()/2);
            comparer.add((int) (maxp.x + tech2.width()/2));
            comparer.add((int) (maxp.y + tech2.height()/2));
            list.add((int) (maxp.x));
            list.add((int) (maxp.y));
            list.add((int) (maxp.x + tech2.width()));
            list.add((int) (maxp.y + tech2.height()));
            dst = mate.clone();
            if(maxval >= threshold)
            {
                System.out.println(maxval);
                System.out.println(find);
                finder++;
                Imgproc.rectangle(mate, maxp, new Point(maxp.x + tech2.cols(),
                        maxp.y + tech2.rows()), new Scalar(0, 255, 0),5);
                Imgproc.rectangle(outputImage, maxp, new Point(maxp.x + tech2.cols(),
                        maxp.y + tech2.rows()), new Scalar(0, 255, 0),-1);
            }else{
                break;
            }
        }
        Imgcodecs.imwrite("C:\\Users\\madse\\IdeaProjects\\ColorBot\\Resources\\images\\sonuc1.png", dst);
    }
    public static void ArrayFilter(ArrayList<Integer> list){
        for(int i = 0; i < list.size(); i += 2){
            int cc = list.get(i);
            int cc1 = list.get(i+1);
            for(int c = 0; c < list.size(); c += 2){
                int f = list.get(c);
                int f1 = list.get(c+1);
                if(i != c){
                    if(cc-5 <= f && cc+5 >= f){
                        if(cc1-5 <= f1 && cc1+5 >= f1){
                            list.remove(c);
                            list.remove(c);
                            c -= 2;
                        }
                    }
                }
            }
        }
        System.out.println(list);
    }
    public static void SearchImageUnless(String firstimg, String secimg, Integer x, Integer y) throws IOException {
        list.clear();
        comparer.clear();
        gems.clear();
        ArrayList<Integer> e = new ArrayList<>();
        FindMultipleImage(firstimg,.85);
        for(int i : comparer){
            e.add(i);
        }
        list.clear();
        comparer.clear();
        FindMultipleImage(secimg,.85);
        System.out.println(e);
        System.out.println(list);
        System.out.println("AFTER");
        ArrayFilter(e);
        ArrayFilter(list);
        for(int i = 0; i < e.size(); i+=2){
            for(int c = 0; c < list.size(); c+=4){
                if(e.get(i)+x >= list.get(c) && e.get(i+1)+y >= list.get(c+1)){
                    if(e.get(i)+x <= list.get(c+2) && e.get(i+1)+y <= list.get(c+3)){
                        System.out.println("match");
                        gems.add(e.get(i));
                        gems.add(e.get(i+1));
                        System.out.println(gems);
                    }
                }
            }
        }
    }
    public static Mat BufferedImage2Mat(BufferedImage image) throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        return Imgcodecs.imdecode(new MatOfByte(byteArrayOutputStream.toByteArray()), Imgcodecs.IMREAD_UNCHANGED);

    }

    public static void PressMouse() throws InterruptedException {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        Thread.sleep(1000);
        UpdatePicture();
    }

    public static void UpdatePicture(){
        screenshot = robot.createScreenCapture(area);
    }

    private static void StaticYCoordClicker(Integer Lowerbound, Integer Upperbound, Integer Ycoord, Color melody) throws InterruptedException {
        for(int i = Lowerbound; i < Upperbound; i++){
            Color pixelColor = new Color(screenshot.getRGB(i, Ycoord));
            if(pixelColor.getRGB() == melody.getRGB()){
                robot.mouseMove(i,126);
                PressMouse();
                break;
            }
        }
    }

    private static void GatherResources() throws InterruptedException {
        robot.mouseMove(67,275);
        PressMouse();
        Thread.sleep(2000);
        //click oil
        StaticYCoordClicker(200,500,126,Items.oil);
        Thread.sleep(1000);
        //click gold
        StaticYCoordClicker(550,700,122,Items.gold);
        Thread.sleep(1000);
        //exit resource interface
        robot.mouseMove(1700,131);
        PressMouse();
    }
    private static void Academy() throws InterruptedException, IOException {
        Thread.sleep(1000);
        robot.mouseMove(830,1000); //click bar at bottom
        PressMouse();
        Thread.sleep(1000);
        robot.mouseMove(400,550); //click academy
        PressMouse();
        Thread.sleep(1000);
        ImageLooper("academy",.8);
        robot.mouseMove(1048,253); //TACTICAL CLASS SKILL BOOK TRAINING
        PressMouse();
        ImageLooper("confirmbutton",.85,2.0);
        robot.mouseMove(robox,roboy);
        PressMouse();
        finder = 0;
        ImageLooper("skillbook",.95,2.0);
        if(finder > 1){
            robot.mouseMove(robox,roboy);
            PressMouse();
            FindMultipleImage("skillstart",.85);
            robot.mouseMove(robox,roboy);
            PressMouse();
            ImageLooper("confirmbutton",.85,2.0);
            robot.mouseMove(robox,roboy);
            PressMouse();
            Thread.sleep(3000);
        }
        ImageLooper("skillbook",.85,2.0);
        if(finder > 1){
            robot.mouseMove(robox,roboy);
            PressMouse();
            FindMultipleImage("skillstart",.85);
            robot.mouseMove(robox,roboy);
            PressMouse();
            ImageLooper("confirmbutton",.85,2.0);
            robot.mouseMove(robox,roboy);
            PressMouse();
            Thread.sleep(3000);
        }
        ImageLooper("confirmbutton",.85,3.0);
        robot.mouseMove(robox,roboy);
        PressMouse();
        finder = 0;
        ImageLooper("confirmbutton",.85,1.5);
        if(finder > 0) {
            robot.mouseMove(robox, roboy);
            PressMouse();
            finder = 0;
        }else {
            ImageLooper("skillbook", .95, 2.0);
            if (finder > 1) {
                robot.mouseMove(robox, roboy);
                PressMouse();
                FindMultipleImage("skillstart", .85);
                robot.mouseMove(robox, roboy);
                PressMouse();
                ImageLooper("confirmbutton", .85, 2.0);
                robot.mouseMove(robox, roboy);
                PressMouse();
                Thread.sleep(3000);
            }
            ImageLooper("skillbook", .85, 2.0);
            if (finder > 1) {
                robot.mouseMove(robox, roboy);
                PressMouse();
                FindMultipleImage("skillstart", .85);
                robot.mouseMove(robox, roboy);
                PressMouse();
                ImageLooper("confirmbutton", .85, 2.0);
                robot.mouseMove(robox, roboy);
                PressMouse();
                Thread.sleep(3000);
            }
        }
        robot.mouseMove(115,122); //back out
        PressMouse();
        ImageLooper("academy",.8);
        robot.mouseMove(1506,284); //click on munitions
        PressMouse();
        ImageLooper("munitions",.9);
        FindMultipleImage("generaltab",.8);
        robot.mouseMove(robox,roboy);
        PressMouse();
        finder=0;
        FindMultipleImage("wisdomcube",.8);
        if(finder > 0){
            robot.mouseMove(robox,roboy);
            PressMouse();
            ImageLooper("exchangebutton",.8);
            FindMultipleImage("exchangebutton",.8);
            robot.mouseMove(robox,roboy);
            PressMouse();
            Thread.sleep(2000);
            finder = 0;
            UpdatePicture();
            mate = BufferedImage2Mat(screenshot);
            FindMultipleImage("confirmbutton",.8);
            Thread.sleep(2000);
            if(finder > 0){
                UpdatePicture();
                mate = BufferedImage2Mat(screenshot);
                FindMultipleImage("closebutton",.8);
                robot.mouseMove(robox,roboy);
                PressMouse();
            }
        }
        finder = 0;
        FindMultipleImage("t3plate",.8);
        if(finder > 0){
            robot.mouseMove(robox,roboy);
            PressMouse();
            ImageLooper("exchangebutton",.8);
            FindMultipleImage("exchangebutton",.8);
            robot.mouseMove(robox,roboy);
            PressMouse();
            Thread.sleep(2000);
            finder = 0;
            UpdatePicture();
            mate = BufferedImage2Mat(screenshot);
            FindMultipleImage("confirmbutton",.8);
            Thread.sleep(2000);
            if(finder > 0){
                UpdatePicture();
                mate = BufferedImage2Mat(screenshot);
                FindMultipleImage("closebutton",.8);
                robot.mouseMove(robox,roboy);
                PressMouse();
                Thread.sleep(2000);
            }
        }
        ImageLooper("guildshop",.85,1.0);
        if(finder < 1){
            FindMultipleImage("generaltab",.85);
            robot.mouseMove(robox,roboy);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            MouseGlider(50,8,50);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            ImageLooper("guildshop",.85,1.0);
        }
        robot.mouseMove(robox,roboy);
        PressMouse();
        ImageLooper("t4part",.85,2.0);
        if(finder > 0){
            robot.mouseMove(robox,roboy);
            PressMouse();
            ImageLooper("t4gunpart",.9,2.0);
            robot.mouseMove(robox,roboy);
            PressMouse();
            ImageLooper("addt4",.85,3.0);
            if(finder > 0){
                robot.mouseMove(robox,roboy);
                for(int i = 0; i < 5; i++){
                    PressMouse();
                }
            }
            ImageLooper("t4confirm",.85,3.0);
            robot.mouseMove(robox,roboy);
            PressMouse();
            ImageLooper("taptocontinue",.85,3.0);
            robot.mouseMove(robox,roboy);
            PressMouse();
            ImageLooper("shoprefresh",.85,3.0);
            if(finder > 0) {
                robot.mouseMove(robox, roboy);
                PressMouse();
                ImageLooper("confirmbutton", .85, 3.0);
                robot.mouseMove(robox, roboy);
                PressMouse();
                ImageLooper("t4part", .85, 2.0);
                if (finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                    ImageLooper("t4gunpart", .9, 2.0);
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                    ImageLooper("addt4", .85, 3.0);
                    if (finder > 0) {
                        robot.mouseMove(robox, roboy);
                        for (int i = 0; i < 5; i++) {
                            PressMouse();
                        }
                    }
                    ImageLooper("t4confirm", .85, 3.0);
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                    ImageLooper("taptocontinue", .85, 3.0);
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                }
            }
        }
        FindMultipleImage("back", .8);
        robot.mouseMove(robox,roboy);
        PressMouse();
        ImageLooper("academy",.8);
        FindMultipleImage("back", .8);
        PressMouse();
        ImageLooper("formation",.8);
        System.out.println("DONE");
    }
    private static void IntroScreenBreakthrough() throws IOException, InterruptedException {
        while(finder.equals(0)){
            UpdatePicture();
            if(finder.equals(0)){
                mate = BufferedImage2Mat(screenshot);
                FindMultipleImage("update",.8);
                if(finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                    finder = 0;
                }
                FindMultipleImage("continueupdate",.8);
                if(finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                    finder = 0;
                }
            }
            mate = BufferedImage2Mat(screenshot);
            FindMultipleImage("Twitter",.59);
            Thread.sleep(500);
        }
        finder = 0;
        robot.mouseMove(900,500);
        PressMouse();
        Thread.sleep(2000);
        PressMouse();
        Thread.sleep(15000);
        UpdatePicture();
        while(finder.equals(0)) {
            UpdatePicture();
            mate = BufferedImage2Mat(screenshot);
            FindMultipleImage("formation",.8);
            if(finder.equals(0)){
                FindMultipleImage("guildnotice",.8);
                if(finder > 0) {
                    GuildSetter();
                }
                FindMultipleImage("taptocontinue",.8);
                if(finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                }
                FindMultipleImage("closebutton",.8);
                if(finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                }
                FindMultipleImage("back",.8);
                if(finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                }
                PressMouse();
                finder = 0;
                System.out.print("yes");
            }
            Thread.sleep(500);
        }
    }

private static void CollectMail() throws IOException, InterruptedException {
        FindMultipleImage("mail",.9);
        robot.mouseMove(robox,roboy);
        PressMouse();
        ImageLooper("collectall",.9);
        robot.mouseMove(robox,roboy);
        PressMouse();
        //loop for collecting all items here
}
private static void GuildSetter() throws IOException, InterruptedException {
    FindMultipleImage("guildgo",.9);
    robot.mouseMove(robox, roboy);
    PressMouse();
    Thread.sleep(5000);
    finder = 0;
    UpdatePicture();
    FindMultipleImage("actionreport",.9);
    if(finder > 0) {
        System.out.println("actionreportfound");
        robot.mouseMove(robox, roboy);
        PressMouse();
        ImageLooper("actionreportclaimall", .9);
        FindMultipleImage("actionreportclaimall",.9);
        robot.mouseMove(robox, roboy);
        PressMouse();
        Thread.sleep(1000);
        PressMouse();
        Thread.sleep(1000);
        FindMultipleImage("actionreportclose", .9);
        robot.mouseMove(robox, roboy);
        PressMouse();
        finder = 0;
    }
    FindMultipleImage("actionreportfindblue",.8);
    if(finder > 0){
        System.out.println("bluefound");
        robot.mouseMove(robox, roboy);
        PressMouse();
        PressMouse();
        ImageLooper("quickdispatch",.9);
        FindMultipleImage("quickdispatch",.9);
        robot.mouseMove(robox, roboy);
        PressMouse();
        ImageLooper("quickdispatchplus",.9);
        FindMultipleImage("quickdispatchrecommend",.8);
        robot.mouseMove(robox, roboy);
        PressMouse();
        Thread.sleep(3000);
        FindMultipleImage("dispatchfleet",.9);
        robot.mouseMove(robox, roboy);
        PressMouse();
        FindMultipleImage("confirmbutton",.9);
        robot.mouseMove(robox, roboy);
        PressMouse();
        ImageLooper("inprogress",.8);
        FindMultipleImage("actionreportclose",.85);
        robot.mouseMove(robox, roboy);
        PressMouse();
        finder = 0;
    }
    FindMultipleImage("actionreportfindyellow",.8);
    if(finder > 0){
        System.out.println("yellowfound");
        robot.mouseMove(robox, roboy);
        PressMouse();
        robot.mouseMove(robox, roboy-100);
        PressMouse();
        ImageLooper("quickdispatch",.9);
        FindMultipleImage("quickdispatch",.9);
        robot.mouseMove(robox, roboy);
        PressMouse();
        ImageLooper("quickdispatchplus",.9);
        FindMultipleImage("quickdispatchrecommend",.8);
        robot.mouseMove(robox, roboy);
        PressMouse();
        Thread.sleep(3000);
        FindMultipleImage("dispatchfleet",.9);
        robot.mouseMove(robox, roboy);
        PressMouse();
        FindMultipleImage("confirmbutton",.9);
        robot.mouseMove(robox, roboy);
        PressMouse();
        ImageLooper("inprogress",.9);
        FindMultipleImage("actionreportclose",.85);
        robot.mouseMove(robox, roboy);
        PressMouse();
        finder = 0;
    }
        //logistics guild contributions here
    FindMultipleImage("logistics",.85);
    if(finder > 0) {
        robot.mouseMove(robox, roboy);
        PressMouse();
        finder = 0;
        ImageLooper("logisticschecker", .85);
        finder = 0;
        FindMultipleImage("collectrewards", .85);
        if (finder > 0) {
            robot.mouseMove(robox, roboy);
            PressMouse();
            ImageLooper("taptocontinue", .8);
            PressMouse();
            finder = 0;
        }
        for (int i = 0; i < 4; i++) {
            UpdatePicture();
            FindMultipleImage("exercisepoints", .9);
            if (finder > 0) {
                System.out.println("Checking exercise");
                robot.mouseMove(robox, roboy + 170);
                PressMouse();
                Thread.sleep(1000);
                FindMultipleImage("confirmbutton", .85);
                robot.mouseMove(robox, roboy);
                PressMouse();
                PressMouse();
                finder = 0;
                continue;
            }
            FindMultipleImage("coins", .9);
            if (finder > 0) {
                UpdatePicture();
                System.out.println("Checking coins");
                robot.mouseMove(robox, roboy + 170);
                PressMouse();
                Thread.sleep(1000);
                FindMultipleImage("confirmbutton", .85);
                robot.mouseMove(robox, roboy);
                PressMouse();
                PressMouse();
                finder = 0;
                continue;
            }
        }
    }
    FindMultipleImage("homebutton",.85);
    robot.mouseMove(robox, roboy);
    PressMouse();
    Thread.sleep(4000);
}
    private static void Dorm() throws IOException, InterruptedException {
        FindMultipleImage("hq",.8);
        robot.mouseMove(robox,roboy);
        PressMouse();
        ImageLooper("dorm",.8);
        robot.mouseMove(robox,roboy);
        PressMouse();
        ImageLooper("confirmbutton",.5,6.0);
        if(finder > 0) {
            System.out.println("confirming");
            robot.mouseMove(robox, roboy);
            PressMouse();
        }
        ImageLooper("givefood",.85,3.0);
        System.out.println("givepass");
        if(finder > 0){
            robot.mouseMove(robox,roboy);
            PressMouse();
        }
        ImageLooper("train",.5,3.0);
        if(finder > 0) {
            robot.mouseMove(robox, roboy + 150);
            PressMouse();
        }
        finder=0;
        while(finder < 1){
            System.out.println("findiindinfind");
            FindMultipleImage("mainfood",.9);
            if(finder > 0) {
                robot.mouseMove(robox, roboy);
                PressMouse();
                finder = 0;
                FindMultipleImage("restockfail",.8);
                if(finder > 0){
                    ImageLooper("foodsupplies",.9,1.5);
                    System.out.println("found food supplies");
                    robot.mouseMove(robox, roboy-100);
                    PressMouse();
                    ImageLooper("foodback",.9,1.5);
                    System.out.println("found back");
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                    finder = 0;
                    break;
                }
                continue;
            }
            FindMultipleImage("mainfoodempty",.9);
            if(finder > 0) {
                robot.mouseMove(robox, roboy);
                PressMouse();
                ImageLooper("plus10food",.9,2.0);
                if(finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                    FindMultipleImage("confirmfood",.9);
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                    finder = 0;
                    continue;
                }
            }
            FindMultipleImage("closebutton",.9);
            if(finder > 0){
                robot.mouseMove(robox, roboy);
                PressMouse();
                finder = 0;
            }
            FindMultipleImage("bluedrink",.9);
            while(finder > 0) {
                ImageLooper("bluedrink",.9,1.0);
                if(finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                    finder = 0;
                    continue;
                }
            }
            finder = 0;
            FindMultipleImage("closebutton",.9);
            if(finder > 0){
                robot.mouseMove(robox, roboy);
                PressMouse();
                finder = 0;
            }
            FindMultipleImage("cokedrink",.9);
            while(finder > 0) {
                ImageLooper("cokedrink",.9,1.0);
                if(finder > 0) {
                    robot.mouseMove(robox, roboy);
                    PressMouse();
                    finder = 0;
                    continue;
                }
            }
            FindMultipleImage("closebutton",.9);
            if(finder > 0){
                robot.mouseMove(robox, roboy);
                PressMouse();
                finder = 0;
            }
            ImageLooper("foodsupplies",.9,1.5);
            System.out.println("found food supplies");
            robot.mouseMove(robox, roboy-100);
            PressMouse();
            ImageLooper("foodback",.9,1.5);
            System.out.println("found back");
            robot.mouseMove(robox, roboy);
            PressMouse();
        }
    }
    private static void Laboratory(){
        System.out.println("This is too hard");
        //ths is kinda hard...
    }

    public static void main(String[] args) throws AWTException, InterruptedException, IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        dailyraidstest tester = new dailyraidstest();
        //tester.dailyraids();
        //FindMultipleImage("skillbook",.95);
        //SearchImageUnless("emptycommission","commissioncube", 966, -45);
        //LoopAndClickUntilGone("testt",.8,3.0,0.0);
        BattleBot.battle();
        System.out.println("end here");
        IntroScreenBreakthrough(); //- work on listeners to do this OR NOT APPARENTLY
        finder = 0;
        Thread.sleep(4000);
        GatherResources();
        Academy();
        Dorm();
        Laboratory();
        BattleBot.battle();
    }
}
class dailyraidstest extends main{
    public static Integer escortmission = 0;
    public static Integer fierceassault = 0;
    public static Integer advancemission = 0;

    public dailyraidstest() throws AWTException {
    }

    public void MissionLooper() throws IOException, InterruptedException {
        finder = 0;
        while(true){
            System.out.println("not breaking");
            FindMultipleImage("escortmission",.85);
            if(finder > 0 && escortmission != 2){
                robot.mouseMove(robox,roboy);
                PressMouse();
                ImageLooper("back",.85);
                robot.mouseMove(robox, roboy);
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
                ImageLooper("back",.85);
                robot.mouseMove(robox, roboy);
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
                ImageLooper("back",.85);
                robot.mouseMove(robox, roboy);
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
    public void dailyraids() throws IOException, InterruptedException {
        ImageLooper("tacticaltraining", .9);
        if (finder > 0) {
            System.out.println("searching");
            robot.mouseMove(robox, roboy);
            PressMouse();
            Thread.sleep(2000);
            finder = 0;

            ImageLooper("back",.85);
            robot.mouseMove(robox, roboy);
            PressMouse();
        }

        for(int i = 0; i < 3; i++) {
            MissionLooper();
            if (escortmission.equals(1)) {
                escortmission = 2;
                ImageLooper("escortmissiongo", .9, 30.0);
                ImageLooper("back",.85);
                robot.mouseMove(robox, roboy);
                PressMouse();
            }
            if (fierceassault.equals(1)) {
                fierceassault = 2;
                ImageLooper("fierceassaultgo", .9, 30.0);
                ImageLooper("back",.85);
                robot.mouseMove(robox, roboy);
                PressMouse();
            }
            if (advancemission.equals(1)) {
                advancemission = 2;
                ImageLooper("advancemissiongo", .9, 30.0);
                ImageLooper("back",.85);
                robot.mouseMove(robox, roboy);
                PressMouse();
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
}