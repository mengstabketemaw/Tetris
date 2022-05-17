package tetris;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Form {
    int SIZE = 25;
    int VAL ;
    Rectangle a = new Rectangle(SIZE-1,SIZE-1),
              b= new Rectangle(SIZE-1,SIZE-1),
              c= new Rectangle(SIZE-1,SIZE-1),
              d= new Rectangle(SIZE-1,SIZE-1);
     String name;
     Color color;
     public int form=1;
     
     public Form(){
       Random rand = new Random();
       int xx = rand.nextInt(7);
       init(xx);
     }
     private Form(int x){
         init(x);
     }
     private void init(int xx){
       int x=300/2;
       int y=25;
       switch(xx){
           case 0:{name = "L";color = Color.BLUEVIOLET; VAL=0;
           a.setX(x);
           a.setY(y);
           b.setX(x);
           b.setY(y+25);
           c.setX(x);
           c.setY(y+50);
           d.setX(x-25);
           d.setY(c.getY());
           break;}
           case 1:{name = "LL";color = Color.DARKTURQUOISE; VAL=1;
           a.setX(x);
           a.setY(y);
           b.setX(x);
           b.setY(y+25);
           c.setX(x);
           c.setY(y+50);
           d.setX(x+25);
           d.setY(c.getY());
           break;}
           case 2:{name ="R";color = Color.SADDLEBROWN; VAL=2;
           a.setX(x);
           a.setY(y);
           b.setX(x+25);
           b.setY(y);
           c.setX(x);
           c.setY(y+25);
           d.setX(x+25);
           d.setY(y+25);
           break;}
           case 3:{name ="Z";color = Color.BLACK; VAL=3;
           a.setX(x-25);
           a.setY(y);
           b.setX(x);
           b.setY(y);
           c.setX(x);
           c.setY(y+25);
           d.setX(x+25);
           d.setY(y+25);
           break;}
           case 4:{name ="LZ";color = Color.TOMATO; VAL=4;
           a.setX(x+25);
           a.setY(y);
           b.setX(x);
           b.setY(y);
           c.setX(x);
           c.setY(y+25);
           d.setX(x-25);
           d.setY(y+25);
           break;}
           case 5:{name ="T";color = Color.WHITESMOKE; VAL=5;
           a.setX(x);
           a.setY(y);
           b.setX(x);
           b.setY(y+25);
           c.setX(x-25);
           c.setY(y+25);
           d.setX(x+25);
           d.setY(y+25);
           break;}
           case 6:{name ="I";color = Color.RED; VAL=6;
           a.setX(x);
           a.setY(y);
           b.setX(x);
           b.setY(y+25);
           c.setX(x);
           c.setY(y+50);
           d.setX(x);
           d.setY(y+75);
           break;}
       }
       a.setFill(color);
       b.setFill(color);
       c.setFill(color);
       d.setFill(color);
       //toForm();
    }

   public  void toForm() {
       switch(name){
           case "L":{
           if(form==1){
               a.setX(d.getX()+25);
               a.setY(d.getY());
               c.setX(b.getX());
               c.setY(b.getY()+25);
               d.setX(c.getX()-25);
               d.setY(c.getY());
           }else if(form==2){
               a.setX(a.getX()-50);
               b.setY(b.getY()-25);
               b.setX(b.getX()-25);
               c.setY(a.getY());
               d.setY(c.getY()+25);
               d.setX(c.getX());
           }
           else if(form==3){
          
               a.setX(b.getX());
               a.setY(b.getY()+50);
               b.setY(a.getY()-25);
               c.setX(b.getX());
               c.setY(b.getY()-25);
               d.setY(c.getY());
               d.setX(c.getX()+25);
           }
           else if(form==4){
               d.setX(c.getX());
               d.setY(c.getY());
               c.setY(d.getY()+25);
               b.setX(c.getX()+25);
               a.setX(b.getX()+25);
               a.setY(b.getY());
           }
           break;
          }
       
         case "LL":{
           if(form==1){
               a.setX(b.getX());
               a.setY(b.getY());
               b.setY(a.getY()+25);
               c.setX(b.getX());
               c.setY(b.getY()+25);
               d.setX(c.getX()+25);
               d.setY(c.getY());
           }else if(form==2){
               a.setX(b.getX()-25);
               a.setY(b.getY());
               c.setY(b.getY());
               c.setX(b.getX()+25);
               d.setY(c.getY()-25);
               d.setX(c.getX());
           }
           else if(form==3){
               a.setX(b.getX());
               a.setY(b.getY()+25);
               c.setX(b.getX());
               c.setY(b.getY()-25);
               d.setY(c.getY());
               d.setX(c.getX()-25);
           }
           else if(form==4){
               b.setX(d.getX()+25);
               b.setY(d.getY());
               c.setX(b.getX()-25);
               a.setX(b.getX()+25);
               a.setY(b.getY());
               d.setX(c.getX());
               d.setY(c.getY()+25);
           }  
         break;}
      
        case "Z":{
            if(form==1||form==3){
            a.setX(b.getX()-25);
            b.setY(a.getY());
            c.setX(b.getX());
            d.setX(c.getX()+25);
            d.setY(c.getY());
        }else if(form==2||form==4){
            a.setX(b.getX());
            b.setY(a.getY()+25);
            c.setX(b.getX()-25);
            d.setY(c.getY()+25);
            d.setX(c.getX());
        }
        break;
       }
        case "LZ":{
            if(form==1||form==3){
            b.setY(c.getY()-25);
            b.setX(c.getX());
            a.setX(b.getX()+25);
            a.setY(d.getY());
            d.setX(c.getX()-25);
            d.setY(c.getY());
        }else if(form==2||form==4){
            b.setX(c.getX()+25);
            b.setY(c.getY());
            a.setX(b.getX());
            a.setY(d.getY()+25);
            d.setY(c.getY()-25);
            d.setX(c.getX());
        }
        break;
       } 
        case "T":{
            if(form==1){
            a.setX(c.getX());
            a.setY(c.getY());
            c.setX(b.getX()-25);
            c.setY(b.getY());
            d.setX(b.getX()+25);
            d.setY(b.getY());
        }else if(form==2){
            d.setY(a.getY());
            d.setX(a.getX());
            a.setX(b.getX()-25);
            a.setY(b.getY());
            c.setX(b.getX());
            c.setY(b.getY()+25);
        }else if(form==3){
            a.setY(b.getY());
            a.setX(b.getX());
            b.setX(d.getX());
            b.setY(d.getY());
            d.setY(b.getY());
            d.setX(b.getX()-25);
            c.setY(b.getY());
            c.setX(b.getX()+25);
        }else if(form==4){
            c.setX(b.getX());
            c.setY(b.getY());
            b.setX(a.getX());
            b.setY(a.getY());
            a.setX(b.getX()+25);
            a.setY(b.getY());
            d.setY(b.getY()+25);
            d.setX(b.getX());
        }
        break;
       } 
        case "I":{
            if(form==2||form==4){
            a.setX(b.getX()-25);
            a.setY(b.getY());
            c.setX(b.getX()+25);
            c.setY(b.getY());
            d.setX(b.getX()+50);
            d.setY(b.getY());
        }else if(form==1||form==3){
            a.setX(b.getX());
            a.setY(b.getY()-25);
            c.setX(b.getX());
            c.setY(b.getY()+25);
            d.setY(b.getY()+50);
            d.setX(b.getX());
       }
      break;
     } 
    }
   }
   public Form nextForm(){
       Form another = new Form(VAL);
       another.form = this.form;
       if(another.form==4)
           another.form=1;
       else
           another.form++;
       
       another.a.setX(Tetris.form.a.getX());
       another.a.setY(Tetris.form.a.getY());
       
       another.b.setX(Tetris.form.b.getX());
       another.b.setY(Tetris.form.b.getY());
       
       another.c.setX(Tetris.form.c.getX());
       another.c.setY(Tetris.form.c.getY());
       
       another.d.setX(Tetris.form.d.getX());
       another.d.setY(Tetris.form.d.getY());
       another.toForm();
        return another;
   }
   
   
   
   
   
}
