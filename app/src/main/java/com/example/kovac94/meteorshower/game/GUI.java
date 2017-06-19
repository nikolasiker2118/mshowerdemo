package com.example.kovac94.meteorshower.game;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kovac94.meteorshower.MainActivity;
import com.example.kovac94.meteorshower.R;
import com.example.kovac94.meteorshower.score.Leaderboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by kovacmarko168 on 10/12/2016.
 */


public class GUI extends View implements View.OnTouchListener{

    Player player;
    Paint paint;
    List<Meteor> meteors;
    Random randomGenerator = new Random();
    Thread thread = Thread.currentThread();


    public GUI(Context context) {
        super(context);
        paint = new Paint();
        setOnTouchListener(this);
        meteors = new ArrayList<>();
        Game game = null;

    }

    //Player creation
    private void createPlayer() { 

        if (player==null) player = new Player(initRocket());
    }

    //Rocket creation
    private Rocket initRocket() {

        Rocket rocket = new Rocket(getWidth()/2,getHeight()-45,10,10,false);
        
        return rocket;
    }

    /*
        @param we are sending percent as a parametar
        @return for example: how much 23%(percent param) of the current hight
    */
    public double yToPercent(double percent){

        double y = (getHeight()/100)*percent;

        return y;
    }

    public double xToPercent(double percent){

        double x = (getWidth()/100)*percent;

        return x;
    }

    //Meteor creation
    private void initMeteor(){

        //Meteor position and destination
        //Random destinationX
        int minX = 10;
        int maxX = getWidth()-10;
        int destX = randomGenerator.nextInt(maxX-minX) + minX;

        //Random x
        int x = randomGenerator.nextInt(maxX-minX) + minX;
        int y = -20;

        meteors.add(new Meteor(x,y,destX,getHeight(),20));

    }
    private void finnishGame(){
        if (player.getLife() == 0){


            final Dialog dialog = new Dialog(Game.instance);
            dialog.setContentView(R.layout.dialog_layout);
            dialog.setTitle("GAME OVER! YOUR SCORE:" +player.getScore());
            final EditText editText = (EditText) dialog.findViewById(R.id.editText);
            Button button1 = (Button) dialog.findViewById(R.id.button2);
            Button button2 = (Button) dialog.findViewById(R.id.button3);

            button1.setText("PLAY AGAIN");
            button2.setText("CONFIRM");

            button1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Game.instance.recreate();

                }
            });

            button2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editText.getText().toString().isEmpty()){
                        Toast.makeText(getContext(), "You must enter your nickname", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent intent = new Intent(Game.instance.getApplicationContext(), Leaderboard.class);
                        intent.putExtra("Nick", ((EditText) dialog.findViewById(R.id.editText)).getText().toString());
                        intent.putExtra("Score", player.getScore());
                        Game.instance.startActivity(intent);
                        Game.instance.finish();
                    }

                }
            });
            if (!Game.instance.isFinishing()) {
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
            else {
                dialog.dismiss();
                Game.instance.recreate();
            }





        }

    }

    @Override
    public void onDraw(final Canvas canvas){
        super.onDraw(canvas);

        System.out.println(getWidth());
        System.out.println(getHeight());
        /*
            if player is not created
            well, create one
        */
        createPlayer();


        /*
            if no enemys are in game add some to
            enemy holder(meteors) so they can appear on the screen
        */
        if(meteors.isEmpty()) initMeteor();


        /*
            prepare color for player and then
            draw him to the screen using his coordinates
        */
        paint.setColor(Color.argb(255,76,192,128));
        canvas.drawCircle((float)player.getRocket().getX(),(float)player.getRocket().getY(),(float)player.getRocket().getRadius(),paint);


        /*
            allow meteors to move by calling move() method
        */
        for(int i=0;i<meteors.size();i++){

            meteors.get(i).move();

            /*
                if meteor destination is reached remove it from the game,
                create another one and then take one life from the player
            */
            if(meteors.get(i).getX()==meteors.get(i).getDestinationX() && meteors.get(i).getY()==meteors.get(i).getDestinationY()){
                meteors.remove(meteors.get(i));
                initMeteor();
                player.setLife(player.getLife()-1);
                finnishGame();



            /*
                @WARRNING@
                this part of the code is reserved for end game
                if player life is equal to zero finish the game

                @FINISH THIS PART LATER@
            */
            }





            /*
            prepare color for the meteor and then
            draw it to the screen using his coordinates
            */
            paint.setColor(Color.RED);
            canvas.drawCircle((float)meteors.get(i).getX(),(float)meteors.get(i).getY(),(float)meteors.get(i).getRadius(),paint);


            /* 
                if player destination is reached we have to reset his position
                and make some cool explosion effect
            */
            if (player.getRocket().getX()==player.getRocket().getDestinationX() && player.getRocket().getY()==player.getRocket().getDestinationY() && player.getRocket().getDestinationX()!=getWidth()/2 && player.getRocket().getDestinationY()!=getHeight()-10){

                
                paint.setColor(Color.argb(255,76,192,128));
                player.getRocket().setRadius(50);
                canvas.drawCircle((float)player.getRocket().getX(),(float)player.getRocket().getY(),(float)player.getRocket().getRadius(),paint);

                List<Meteor> destroyMeteors = new ArrayList<>();
                for(int p=0;p<meteors.size();p++){
                    if((meteors.get(p).getX()-player.getRocket().getX())*(meteors.get(p).getX()-player.getRocket().getX())+(meteors.get(p).getY()-player.getRocket().getY())*(meteors.get(p).getY()-player.getRocket().getY())<=(meteors.get(p).getRadius()+player.getRocket().getRadius())*(meteors.get(p).getRadius()+player.getRocket().getRadius())){
                        destroyMeteors.add(meteors.get(p));

                        initMeteor();
                        initMeteor();

                        //if meteor is destroyed you gain 100 points
                        player.setScore(player.getScore()+100);

                    }

                }

                for(int h=0;h<destroyMeteors.size();h++){
                    meteors.remove(destroyMeteors.get(h));

                }

                player.setRocket(initRocket());
            }
        }

        
        player.getRocket().move();

        //Draw score
        paint.setTextSize(30);
        paint.setColor(Color.argb(255,76,192,128));
        canvas.drawText("Score: "+player.getScore(),5,50,paint);

        //Draw life circles
        paint.setColor(Color.argb(255,245,35,84));
        canvas.drawText("Life: ",5,80,paint);
        int distance = 90;
        paint.setColor(Color.argb(255,245,35,84));
        for (int q=0; q<player.getLife(); q++){
            
            canvas.drawCircle(distance,70,10,paint);
            distance+=22;   
        }


        //Force to redraw everything
        invalidate();

        try {
            thread.sleep(22);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction()==MotionEvent.ACTION_DOWN){

            //If player.move = true then you can't change the player destination
            //You can choose player destination only once
            if (player.getRocket().isMoving()!=true){
                player.getRocket().setMoving(true);
                player.getRocket().setDestinationX(event.getX());
                player.getRocket().setDestinationY(event.getY());
            }
        }

        return false;
    }
}



