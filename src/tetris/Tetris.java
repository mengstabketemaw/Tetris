package tetris;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Tetris extends Application {
    BufferedWriter data;
    BufferedReader data1;
    static Form form;
    static Form nextForm;
    boolean makeOne = true;
    Pane pane;
    Text gameover;
    Text score;
    Text highScore;
    Text next;
    Text line;
    int MOVEY=25;
    int HS;
    int STATE=0;  //0 = play , 1 = pause , 2 = GameOver 3 = wating;
    final int SIZE = 25;
    final int XSIZE = SIZE*4*3;
    final int YSIZE = SIZE*4*7-50;
    final int teris[][] = new int[XSIZE/SIZE][YSIZE/SIZE];
    long sleep = 500;
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            data1 = new BufferedReader(new FileReader("data.tis"));
            HS = Integer.parseInt(data1.readLine());
            data1.close();
        } catch (FileNotFoundException fileNotFoundException) {
            FileOutputStream x = new FileOutputStream("data.tis");
            x.write("0".getBytes());
            x.flush();
            x.close();
        }
      
        highScore = new Text(XSIZE+5,150,"High Score :"+HS);
        highScore.setFill(Color.RED);
        highScore.setFont(new Font(20));
        score = new Text(XSIZE+10,50,"Score :0");
        score.setFill(Color.BLUE);
        score.setFont(new Font(20));
        next = new Text(XSIZE+10,YSIZE-250,"Next");
        next.setFont(new Font(30));
        line = new Text(XSIZE+10,100,"Line :0");
        line.setFont(new Font(20));
        line.setFill(Color.GREEN);
        gameover = new Text(10,YSIZE/2,"Game OVER");
        gameover.setFill(Color.DARKBLUE);
        gameover.setFont(new Font(60));
        Line l = new Line(XSIZE,10,XSIZE,YSIZE-10);
        Text gameover = new Text(100,YSIZE/2,"Paused");
        gameover.setFill(Color.DARKBLUE);
        gameover.setFont(new Font(60));
        form = new Form();
        nextForm = new Form();
        pane = new Pane(form.a,form.b,form.c,form.d,l,line,score,next,highScore);
       Thread thread = new Thread(()->{
       for(;;){
       String[] st = score.getText().split(":");
       int val = Integer.parseInt(st[1])+50;
       Platform.runLater(()->{
                if(makeOne)
                if(STATE==0)
                cont(form);
                });
       try {
           Thread.sleep(sleep);
       } catch (InterruptedException ex) {
               System.out.println("error in sleeping the engine thread");
       }
         //  System.out.println(sleep);
         }
       }); 
       thread.start();
       Scene scene = new Scene(pane,XSIZE+185,YSIZE);
       scene.setOnKeyPressed(e->{
       onKeyPressed(e);
        });
       Media media = new Media(getClass().getResource("audio.mp3").toExternalForm());
       MediaPlayer mediaplayer = new MediaPlayer(media);
       mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
       mediaplayer.play();
       mediaplayer.setVolume(0.1);  
               
       primaryStage.focusedProperty().addListener((q,w,e)->{try{
               if(!primaryStage.isFocused()){
                STATE=1;  
                mediaplayer.pause();
                pane.getChildren().add(gameover);
                pane.setBackground(new Background(new BackgroundImage(new Image(new FileInputStream("C:\\Users\\Ketemaw\\Documents\\NetBeansProjects\\Tetris\\jpg1.jpg")),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));}
               else {
                STATE=0; 
                pane.getChildren().remove(gameover);
                pane.setBackground(new Background(new BackgroundFill(new LinearGradient(0,0,1,1,true,CycleMethod.NO_CYCLE,new Stop(0,Color.GREEN),new Stop(0.5,Color.YELLOW),new Stop(1,Color.RED)),new CornerRadii(0),new Insets(0))));
                mediaplayer.play();
               }}catch(Exception ew){
       System.err.print("erro in changing picture");
       }});
       primaryStage.setOnCloseRequest(e->{
       thread.stop(); 
       });
       nextOne();
       primaryStage.setScene(scene);
       primaryStage.show();
}
    
    void cont(Form form){
          if(attota(form)==0){
           form.a.setY(form.a.getY()+MOVEY);
           form.b.setY(form.b.getY()+MOVEY);
           form.c.setY(form.c.getY()+MOVEY);
           form.d.setY(form.d.getY()+MOVEY);}
          else{
            remove();
            anotherOne();
            nextOne();
          }
      }

    int attota(Form form){
         makeOne = false;
         if(form.a.getY()<=YSIZE -50&& form.b.getY()<=YSIZE-50 && form.c.getY()<=YSIZE-50 && form.d.getY()<=YSIZE-50){
              if(teris[(int)((form.a.getX())/25)][(int)((form.a.getY()+25)/25)]==1 ||
               teris[(int)((form.b.getX())/25)][(int)((form.b.getY()+25)/25)]==1 ||
               teris[(int)((form.c.getX())/25)][(int)((form.c.getY()+25)/25)]==1 ||
               teris[(int)((form.d.getX())/25)][(int)((form.d.getY()+25)/25)]==1) {

              teris[(int)form.a.getX()/25][(int)form.a.getY()/25]=1;
              teris[(int)form.b.getX()/25][(int)form.b.getY()/25]=1;
              teris[(int)form.c.getX()/25][(int)form.c.getY()/25]=1;
              teris[(int)form.d.getX()/25][(int)form.d.getY()/25]=1;
              makeOne=true;
              return 1;

                }
             }
             else if(form.a.getY()== YSIZE-25 || form.b.getY() == YSIZE-25 || form.c.getY() == YSIZE-25 || form.d.getY() == YSIZE-25){
              teris[(int)form.a.getX()/25][(int)form.a.getY()/25]=1;
              teris[(int)form.b.getX()/25][(int)form.b.getY()/25]=1;
              teris[(int)form.c.getX()/25][(int)form.c.getY()/25]=1;
              teris[(int)form.d.getX()/25][(int)form.d.getY()/25]=1;
              makeOne=true;
              return 1;
             }
              makeOne=true;
             return 0;
      }

    private void anotherOne() {
          String[] st = score.getText().split(":");
          score.setText(st[0]+":"+(Integer.parseInt(st[1])+50));
          if(form.a.getY()<50||form.b.getY()<50||form.c.getY()<50||form.d.getY()<50)
          STATE = 2;//game OVer
                  if(STATE==0){
                      form = nextForm;
                      pane.getChildren().addAll(form.a,form.b,form.c,form.d);
                      cont(form);
                  }
                  else if(STATE==2){
                  pane.getChildren().add(gameover);
                  if(Integer.parseInt(st[1])>HS){
                      String[] stt = highScore.getText().split(":");
                      highScore.setText(stt[0]+":"+(Integer.parseInt(st[1])+50));

                      Alert alert = new Alert(AlertType.INFORMATION);
                      alert.setHeaderText("Congra High Score");
                      alert.setTitle("HS");
                      alert.setContentText("hey u just achive high score of " + (st[1]+50));
                      alert.setGraphic(new ImageView(getClass().getResource("winner.png").toExternalForm()));
                      alert.showAndWait();
                      try {
                          data = new BufferedWriter(new FileWriter("data.tis"));
                          data.write(st[1]+"");
                          data.close();
                      } catch (IOException ex) {
                          System.out.println("Errot in writing HS to File");}
                      
                      
                        pane.getChildren().removeIf(node->{
                         if(node instanceof Rectangle)
                         return true;
                         return false;
                        });
                        for(int i=0;i<XSIZE/25;i++)
                              Arrays.fill(teris[i], 0);
                        form = new Form();
                        pane.getChildren().addAll(form.a,form.b,form.c,form.d);
                        pane.getChildren().remove(gameover);
                        cont(form);
                        nextOne();
                        STATE=0;
                        sleep=500;
                        String[] sty = score.getText().split(":");
                        score.setText(sty[0]+":"+0);
                        st = line.getText().split(":");
                        line.setText(sty[0]+":"+0);
                      
                  
                  }
                  
                        
                      
                  
                  //STATE = 3;
                  
                  }
      }

    private void onKeyPressed(KeyEvent e) {
       if(KeyCode.RIGHT == e.getCode()){
          if(form.a.getX()<=XSIZE-50 && form.b.getX()<=XSIZE-50 && form.c.getX()<=XSIZE-50 && form.d.getX()<=XSIZE-50)
               {
                 if(teris[(int)(form.a.getX()+25)/25][(int)(form.a.getY())/25]==0 &&
                  teris[(int)(form.b.getX()+25)/25][(int)(form.b.getY())/25]==0 &&
                  teris[(int)(form.c.getX()+25)/25][(int)(form.c.getY())/25]==0 &&
                  teris[(int)(form.d.getX()+25)/25][(int)(form.d.getY())/25]==0){
                   form.a.setX(form.a.getX()+25);
                   form.b.setX(form.b.getX()+25);
                   form.c.setX(form.c.getX()+25);
                   form.d.setX(form.d.getX()+25);}
           }
       }
          else if(KeyCode.LEFT == e.getCode()){

          if(form.a.getX()>=25 && form.b.getX()>=25 && form.c.getX()>=25 && form.d.getX()>=25)
            {
                  if(teris[(int)(form.a.getX()-25)/25][(int)(form.a.getY())/25]==0 &&
                  teris[(int)(form.b.getX()-25)/25][(int)(form.b.getY())/25]==0 &&
                  teris[(int)(form.c.getX()-25)/25][(int)(form.c.getY())/25]==0 &&
                  teris[(int)(form.d.getX()-25)/25][(int)(form.d.getY())/25]==0){
                   form.a.setX(form.a.getX()-25);
                   form.b.setX(form.b.getX()-25);
                   form.c.setX(form.c.getX()-25);
                   form.d.setX(form.d.getX()-25);}
             }
          }
          else if(KeyCode.UP == e.getCode()){
              if(form.name.equals("L")){
                 if((form.d.getX()==0&&form.c.getX()==25&&form.b.getX()==25)||(form.d.getX()==275&&form.c.getX()==250&&form.b.getX()==250))
                     return;
                  Form demo  = form.nextForm();
                 if(teris[(int)demo.a.getX()/25][(int)demo.a.getY()/25]==1||
                    teris[(int)demo.b.getX()/25][(int)demo.b.getY()/25]==1||
                    teris[(int)demo.c.getX()/25][(int)demo.c.getY()/25]==1||
                    teris[(int)demo.d.getX()/25][(int)demo.d.getY()/25]==1)
                     return;

              }
              else if(form.name.equals("LL")){
                if((form.d.getX()==25&&form.c.getX()==0&&form.b.getX()==0)||(form.d.getX()==250&&form.c.getX()==275&&form.b.getX()==275))
                     return;
                  Form demo  = form.nextForm();
                 if(teris[(int)demo.a.getX()/25][(int)demo.a.getY()/25]==1||
                    teris[(int)demo.b.getX()/25][(int)demo.b.getY()/25]==1||
                    teris[(int)demo.c.getX()/25][(int)demo.c.getY()/25]==1||
                    teris[(int)demo.d.getX()/25][(int)demo.d.getY()/25]==1)
                     return;
              }
              else if(form.name.equals("Z")){
                if(form.a.getX()==275&&form.b.getX()==275&&form.c.getX()==250&&form.d.getX()==250)
                return;
                Form demo  = form.nextForm();
                if(teris[(int)demo.a.getX()/25][(int)demo.a.getY()/25]==1||
                   teris[(int)demo.b.getX()/25][(int)demo.b.getY()/25]==1||
                   teris[(int)demo.c.getX()/25][(int)demo.c.getY()/25]==1||
                   teris[(int)demo.d.getX()/25][(int)demo.d.getY()/25]==1)
                   return;
              }
              else if(form.name.equals("LZ")){
                if(form.a.getX()==25&&form.b.getX()==25&&form.c.getX()==0&&form.d.getX()==0)
                 return;
                 Form demo  = form.nextForm();
                 if(teris[(int)demo.a.getX()/25][(int)demo.a.getY()/25]==1||
                   teris[(int)demo.b.getX()/25][(int)demo.b.getY()/25]==1||
                   teris[(int)demo.c.getX()/25][(int)demo.c.getY()/25]==1||
                   teris[(int)demo.d.getX()/25][(int)demo.d.getY()/25]==1)
                   return;
              }
              else if(form.name.equals("T")){
                  if((form.a.getX()==25&&form.b.getX()==0&&form.c.getX()==0)||(form.a.getX()==250&&form.b.getX()==275&&form.c.getX()==275))
                      return;
                 Form demo  = form.nextForm();
                 if(teris[(int)demo.a.getX()/25][(int)demo.a.getY()/25]==1||
                   teris[(int)demo.b.getX()/25][(int)demo.b.getY()/25]==1||
                   teris[(int)demo.c.getX()/25][(int)demo.c.getY()/25]==1||
                   teris[(int)demo.d.getX()/25][(int)demo.d.getY()/25]==1)
                   return;
              }
              else if(form.name.equals("I")){
                 if(form.d.getX()==0||form.a.getX()>=250)
                 return;
                 Form demo  = form.nextForm();
                 if(teris[(int)demo.a.getX()/25][(int)demo.a.getY()/25]==1||
                    teris[(int)demo.b.getX()/25][(int)demo.b.getY()/25]==1||
                    teris[(int)demo.c.getX()/25][(int)demo.c.getY()/25]==1||
                    teris[(int)demo.d.getX()/25][(int)demo.d.getY()/25]==1)
                    return;
                 }
              //if u pass all the a bove restriction u can change the form of the teris
              if(form.form==4)
                  form.form=1;
              else 
                  form.form++;
                  form.toForm();
          }
          else if(KeyCode.DOWN == e.getCode()){
            if(makeOne&&STATE==0)
            cont(form);
          }
       if(e.getCode()==KeyCode.SPACE)
       if(STATE==2){
          pane.getChildren().removeIf(node->{
           if(node instanceof Rectangle)
           return true;
           return false;
          });
          for(int i=0;i<XSIZE/25;i++)
                Arrays.fill(teris[i], 0);
          form = new Form();
          pane.getChildren().addAll(form.a,form.b,form.c,form.d);
          pane.getChildren().remove(gameover);
          cont(form);
          nextOne();
          STATE=0;
          sleep=500;
          String[] st = score.getText().split(":");
          score.setText(st[0]+":"+0);
          st = line.getText().split(":");
          line.setText(st[0]+":"+0);
       }
       }

    private void remove(){
      makeOne=false;
      for(int j=0;j<YSIZE/25;j++){
      int rebo = 0;
      for(int i=0;i<XSIZE/25;i++)
          if(teris[i][j]==1){
              rebo++;}
          if(rebo==12){
          final int ROW = j;
          for(int k=0;k<12;k++)
              teris[k][j]=0;
          pane.getChildren().removeIf(node->{
          if(node instanceof Rectangle)
          if (((Rectangle)node).getY()==ROW*25)
          return true;
          return false;
          });
          pane.getChildren().filtered(node->{
          if(node instanceof Rectangle)
              if(((Rectangle)node).getY()<ROW*25)
               return true;
               return false;
          }).forEach(noe->{
          Rectangle node = (Rectangle)noe;
          node.setY(node.getY()+25);
          });
          for(int v = ROW;v>0;v--)
            for(int i = 0;i<XSIZE/25;i++){
                teris[i][v]=teris[i][v-1];
            }

          String[] st = line.getText().split(":");
          line.setText(st[0]+":"+(Integer.parseInt(st[1])+1));
          if(sleep>10)
            sleep-=10;
         }
          if(j==YSIZE/25-1)
            makeOne = true;
       }
    }

    private void nextOne() {
      nextForm = new Form();
      pane.getChildren().removeIf(node->{
         if(node instanceof Rectangle)
             if(((Rectangle)node).getX()>XSIZE)
             return true;
             return false;
      });
      Rectangle a = new Rectangle(SIZE-1,SIZE-1),
                b = new Rectangle(SIZE-1,SIZE-1),
                c = new Rectangle(SIZE-1,SIZE-1),
                d = new Rectangle(SIZE-1,SIZE-1);
      a.setX(nextForm.a.getX()+150+30);
      b.setX(nextForm.b.getX()+150+30);
      c.setX(nextForm.c.getX()+150+30);
      d.setX(nextForm.d.getX()+150+30);
      a.setY(nextForm.a.getY()+YSIZE-250);
      b.setY(nextForm.b.getY()+YSIZE-250);
      c.setY(nextForm.c.getY()+YSIZE-250);
      d.setY(nextForm.d.getY()+YSIZE-250);
      pane.getChildren().addAll(a,b,c,d);
      }
}