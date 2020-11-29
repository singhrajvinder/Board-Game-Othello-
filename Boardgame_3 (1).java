import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import javafx.stage.Stage;  
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.TranslateTransition; 
import javafx.animation.Animation; 
import javafx.util.Duration;  
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent; 
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

//=========================================================================
// Main Class
//=========================================================================
public class BoardGame_3 extends Application 
{  
    private final int BLACK = 0;
    private final int WHITE = 1;

    Image im_empty = new Image( "emptySquare80.gif" );
    Image im_black = new Image( "blackPiece80.gif" );
    Image im_white = new Image( "whitePiece80.gif" );
    Image im_move_black = new Image( "blackMoveSquare80.gif" );
    Image im_move_white = new Image( "whiteMoveSquare80.gif" );
    
    double width = im_empty.getWidth();
    double height = im_empty.getHeight();
    
    private int turn = BLACK;
    
    private Image im_move_vec[] = {im_black, im_white};
    private Image im_mark_vec[] = {im_move_black, im_move_white};
    
    Image im_on_move = im_move_vec[turn];
    Image im_off_move = im_move_vec[1-turn];
    Image im_movesquare = im_mark_vec[turn];
    
    Canvas canvas = new Canvas( width*8, height*8);
    GraphicsContext gc = canvas.getGraphicsContext2D();

    public static void main(String[] args) { launch(args); }
    
    private final int nrows = 8;
    private final int ncols = 8;
    
    private Image b[][] = new Image[nrows][ncols];
    private final Group group = new Group(canvas);
    private final Scene scene = new Scene(group, width*ncols, height*nrows, Color.BLACK );
    
    //-------------------------------------------------------------------------
    //
    //
    private Image board_contents(int row, int col)
    {
        if(row < 0 || row > 7) return null;
        if(col < 0 || col > 7) return null;
        return b[row][col];
    }
    
//-------------------------------------------------------------------------
// start() method (build the GUI)
//
    @Override public void start(Stage stage) throws Exception 
    {

        
        final Group group = new Group(canvas);
        final Scene scene = new Scene(group, width*ncols, height*nrows, Color.BLACK );
        //--------------------------------------------------------------------
        // Make empty board
        //
        for (int row = 0; row < nrows; row++)
          for (int col = 0; col < ncols; col++)
        {
            gc.drawImage(im_empty, col*width, row*height );
            b[row][col] = im_empty;
        }
        //--------------------------------------------------------------------
        // Put code here to setup initial position
        //
        gc.drawImage(im_black, 3*width, 3*height);
        b[3][3] = im_black;
        gc.drawImage(im_white, 3*width, 4*height);
        b[4][3] = im_white;
        gc.drawImage(im_black, 4*width, 4*height);
        b[4][4] = im_black;
        gc.drawImage(im_white, 4*width, 3*height);
        b[3][4] = im_white;
        
        
        stage.setScene(scene);
        stage.setTitle("Othello");
        stage.show();
        
        //--------------------------------------------------------------------
        // Handle Mouse Events
        //
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() 
        { 
            @Override 
            public void handle(MouseEvent e) 
            { 
                int row = (int)(e.getY()/height);
                int col = (int)(e.getX()/width);
                //--------------------------------------------------------------------
                // Check clicked square is empty.
                // Compute flips. Not a valid move if no flips.
                //
                int flips_made_at[][] = new int[nrows][ncols];
                  //col++
                if(col < 7)
                {
                  int row_pos = row;
                  int col_pos = col;
                  boolean fliper = false;
                        
                  while((b[row_pos][col_pos+1] == im_off_move) && (col_pos+1)<7)
                  {
                     if (b[row_pos][col_pos+2] == im_on_move)
                     {
                        fliper=true;
                        flips_made_at[row][col] = 2;
                     }
                     col_pos++;
                  }
                       
                  while(fliper==true && col_pos>col)
                  {
                     flips_made_at[row_pos][col_pos] = 1;
                     col_pos--;
                  }
                     
                }
                   //coll--
                if(col > 0)
                {
                  int row_pos = row;
                  int col_pos = col;
                  boolean fliper = false;
                        
                  while((b[row_pos][col_pos-1] == im_off_move) && (col_pos-1)>0)
                  {
                     if (b[row_pos][col_pos-2] == im_on_move)
                     {
                        fliper=true;
                        flips_made_at[row][col] = 2;
                     }
                     col_pos--;
                  }
                       
                  while(fliper==true && col_pos<col)
                  {
                     flips_made_at[row_pos][col_pos] = 1;
                     col_pos++;
                  }
                }
                   //row++
                if(row < 7)
                {
                  int row_pos = row;
                  int col_pos = col;
                  boolean fliper = false;
                        
                  while((b[row_pos+1][col_pos] == im_off_move) && (row_pos+1)<7)
                  {
                     if (b[row_pos+2][col_pos] == im_on_move)
                     {
                        fliper=true;
                        flips_made_at[row][col] = 2;
                     }
                     row_pos++;
                  }
                       
                  while(fliper==true && row_pos>row)
                  {
                     flips_made_at[row_pos][col_pos] = 1;
                     row_pos--;
                  }
                     
                }
                   //row--
                if(row > 0)
                {
                  int row_pos = row;
                  int col_pos = col;
                  boolean fliper = false;
                        
                  while((b[row_pos-1][col_pos] == im_off_move) && (row_pos-1)>0)
                  {
                     if (b[row_pos-2][col_pos] == im_on_move)
                     {
                        fliper=true;
                        flips_made_at[row][col] = 2;
                     }
                     row_pos--;
                  }
                       
                  while(fliper==true && row_pos<row)
                  {
                     flips_made_at[row_pos][col_pos] = 1;
                     row_pos++;
                  }
                     
                }
                   
                   //row++ col++
                if(row < 7 && col < 7)
                {
                  int row_pos = row;
                  int col_pos = col;
                  boolean fliper = false;
                        
                  while((b[row_pos+1][col_pos+1] == im_off_move) && ((row_pos+1)<7 && col_pos+1<7))
                  {
                     if (b[row_pos+2][col_pos+2] == im_on_move)
                     {
                        fliper=true;
                        flips_made_at[row][col] = 2;
                     }
                     row_pos++;
                     col_pos++;
                  }
                       
                  while(fliper==true && row_pos>row && col_pos>col)
                  {
                     flips_made_at[row_pos][col_pos] = 1;
                     row_pos--;
                     col_pos--;
                  }
                }
                   //row-- col--
                if(row > 0 && col > 0)
                {
                  int row_pos = row;
                  int col_pos = col;
                  boolean fliper = false;
                        
                  while((b[row_pos-1][col_pos-1] == im_off_move) && ((row_pos-1)>0 && col_pos-1>0))
                  {
                     if (b[row_pos-2][col_pos-2] == im_on_move)
                     {
                        fliper=true;
                        flips_made_at[row][col] = 2;
                     }
                     row_pos--;
                     col_pos--;
                  }
                       
                  while(fliper==true && row_pos<row && col_pos<col)
                  {
                     flips_made_at[row_pos][col_pos] = 1;
                     row_pos++;
                     col_pos++;
                  }
                }
                   //row++ col--
                if(row < 7 && col > 0)
                {
                  int row_pos = row;
                  int col_pos = col;
                  boolean fliper = false;
                        
                  while((b[row_pos+1][col_pos-1] == im_off_move) && ((row_pos+1)<7 && col_pos-1>0))
                  {
                     if (b[row_pos+2][col_pos-2] == im_on_move)
                     {
                        fliper=true;
                        flips_made_at[row][col] = 2;
                     }
                     row_pos++;
                     col_pos--;
                  }
                       
                  while(fliper==true && row_pos>row && col_pos<col)
                  {
                     flips_made_at[row_pos][col_pos] = 1;
                     row_pos--;
                     col_pos++;
                  }   
                }
                   //row-- col++
                if(row>0 && col<7)
                {
                  int row_pos = row;
                  int col_pos = col;
                  boolean fliper = false;
                        
                  while((b[row_pos-1][col_pos+1] == im_off_move) && ((row_pos-1)>0 && col_pos+1<7))
                  {
                     if (b[row_pos-2][col_pos+2] == im_on_move)
                     {
                        fliper=true;
                        flips_made_at[row][col] = 2;
                     }
                     row_pos--;
                     col_pos++;
                  }
                       
                  while(fliper==true && row_pos<row && col_pos>col)
                  {
                     flips_made_at[row_pos][col_pos] = 1;
                     row_pos++;
                     col_pos--;
                  }
                }


                if (b[row][col] == im_empty && flips_made_at[row][col]==2)
                { 
                  gc.drawImage(im_on_move, col*width, row*height );
                  b[row][col] = im_on_move; 
                  
                  for(int r=0; r<8; r++)
                  {
                     for(int c=0; c<8; c++)
                     {
                        if(flips_made_at[r][c] == 1)
                        {
                           gc.drawImage(im_on_move, c*width, r*height );
                           b[r][c] = im_on_move;
                        }
                     }
                  }
                  
                  Move new_move = new Move(turn,row,col,flips_made_at);
                  
                  turn = 1 - turn;
                  im_movesquare = im_mark_vec[turn];
                  im_on_move = im_move_vec[turn];
                  im_off_move = im_move_vec[1-turn];
                  System.out.println((int)(e.getY()/height) + ", " + (int)(e.getX()/width)); 
                }
            } 
        };// end EventHandler<MouseEvent>

        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
  }// end start 
    
//-------------------------------------------------------------------------
// Move class (stores a move object)
//    
  private class Move
  {
      int turn;
      int row_move;
      int col_move;
      int flip_count[][];
      
      public Move(int t, int ro, int co, int flip_er[][])
      {
         turn = t;
         row_move = ro;
         col_move = co;
         flip_count = flip_er;
      }
  }
  
}// end class Othello