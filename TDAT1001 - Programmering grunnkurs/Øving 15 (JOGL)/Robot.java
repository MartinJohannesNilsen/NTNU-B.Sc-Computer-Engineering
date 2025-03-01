bplist00�XUTI-Data�_$com.apple.traditional-mac-plain-text_public.utf8-plain-text_public.utf16-plain-textOHimport java.awt.event.KeyAdapter;import java.awt.event.KeyEvent;import com.jogamp.opengl.GL;import java.awt.*;import javax.swing.*;import com.jogamp.opengl.GL2;import com.jogamp.opengl.glu.GLU;import com.jogamp.opengl.GLAutoDrawable;import com.jogamp.opengl.GLEventListener;import com.jogamp.opengl.awt.GLCanvas;import com.jogamp.opengl.util.FPSAnimator;import com.jogamp.opengl.util.gl2.GLUT;/** * NeHe Lesson #2 (JOGL 2 Port): Basic 2D Shapes * @author Tomas Holt, based on code from Hock-Chuan Chua (May 2012) * @version October 2016 *//* Main class which extends GLCanvas. This means that this is a OpenGL canvas.   We will use OpenGL commands to draw on this canvas.   This implementation has no animation or user input.*/public class Robot extends GLCanvas implements GLEventListener {   // constants   private static String TITLE = "Hater JOGL";   private static final int CANVAS_WIDTH = 320;  // width of the drawable   private static final int CANVAS_HEIGHT = 240; // height of the drawable     // Setup OpenGL Graphics Renderer    private GLU glu;  // for the GL Utility   private GLUT glut = new GLUT();      /** Constructor to setup the GUI for this Component */   public Robot() {      this.addGLEventListener(this);      this.addKeyListener(new RotateKeyListener());   }   // ------ Implement methods declared in GLEventListener (init,reshape,display,dispose)             /**    * Called immediately after the OpenGL context is initialized. Can be used     * to perform one-time initialization. Run only once.    */   public void init(GLAutoDrawable drawable) {      GL2 gl = drawable.getGL().getGL2();      // get the OpenGL graphics context      glu = new GLU();                         // get GL Utilities      gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color      gl.glEnable(GL2.GL_DEPTH_TEST); // enables depth testing      gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); // best perspective correction   }   /**    * Handler for window re-size event. Also called when the drawable is     * first set to visible    */   public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {      GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context      if (height == 0) height = 1;   // prevent divide by zero           float aspect = (float)width / height;      //Set the view port (display area) to cover the entire window      //gl.glViewport(0, 0, width/2, height/2);      // Setup perspective projection, with aspect ratio matches viewport      gl.glMatrixMode(GL2.GL_PROJECTION);  // choose projection matrix      gl.glLoadIdentity();             // reset projection matrix      glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect, zNear, zFar      // Enable the model-view transform      gl.glMatrixMode(GL2.GL_MODELVIEW);      gl.glLoadIdentity(); // reset   }   /**    * Called by OpenGL for drawing    */   private double vinkel1 = -90;   private double vinkel2 = 90;   private int flytteBein = 0;      GLUT kube = new GLUT();      public void tegnBein(GL2 gl){      gl.glTranslatef(-4.5f, -3.5f, 0.0f);           gl.glPushMatrix();      if (vinkel1 < 0) vinkel1-=flytteBein;      gl.glRotated(vinkel1, 1, 0, 0);      kube.glutSolidCube(2);      gl.glPopMatrix();            gl.glPushMatrix();      if(vinkel2 > 0) vinkel2+=flytteBein;      gl.glTranslatef(3f, 0.0f, 0.0f);      gl.glRotated(vinkel2, 1, 0, 0);      kube.glutSolidCube(2);      gl.glPopMatrix();   }      float konstantC = 0;   float kameravinkel = 0;   int r = -60;   private double gluLookAtX = java.lang.Math.cos(kameravinkel) * r;   private double gluLookAtZ = java.lang.Math.sin(kameravinkel) * r;      public void display(GLAutoDrawable drawable) {      GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context      gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers      gl.glLoadIdentity();  // reset the model-view matrix                   gluLookAtX = java.lang.Math.cos(kameravinkel) * r;      gluLookAtZ = java.lang.Math.sin(kameravinkel) * r;      glu.gluLookAt(gluLookAtX, 0, gluLookAtZ+konstantC, 0, 0, konstantC, 0, 1, 0);            gl.glTranslatef(0f, 0.0f, konstantC);            //Kropp      gl.glColor3d(0, 0, 1);      kube.glutSolidCube(5);            //Hode      gl.glTranslatef(0f, 4f, 0.0f);      gl.glColor3d(1, 0, 0);      kube.glutSolidCube(3);            //H�yre arm      gl.glTranslatef(-3f, -4.0f, 0.0f);      gl.glColor3d(1, 1, 0);      kube.glutSolidCube(1);            //Venstre arm      gl.glTranslatef(6f, 0.0f, 0.0f);      kube.glutSolidCube(1);            gl.glColor3d(0, 1, 0);      tegnBein(gl);      gl.glTranslatef(3f, 0.0f, 0.0f);            //venstre �ye      gl.glTranslatef(-1f, 8f, 2.0f);      gl.glColor3d(1, 1, 1);      kube.glutSolidCube(1);            //h�yre �ye      gl.glTranslatef(-1.5f,0,0);      kube.glutSolidCube(1);   }      private class RotateKeyListener extends KeyAdapter{       public void keyPressed(KeyEvent e){           System.out.println("Lytter kalt");           if(e.getKeyChar() == 'd'){             kameravinkel += 0.1;           }           if(e.getKeyChar() == 'a'){             kameravinkel -= 0.1;            }           if(e.getKeyChar() == 'w'){             konstantC += 5;             flytteBein = 50;           }           if(e.getKeyChar() == 's'){             konstantC += -5;             flytteBein = 50;            }                      Robot.this.repaint();                }        }      private void drawAllAxis(GL2 gl){      gl.glColor3d(1, 0, 0);            double[] tabell = {2,0,0};      drawAxis(gl,tabell);            gl.glColor3d(0, 1, 0);      //y-akse            double[] tabell2 = {0,2,0};      drawAxis(gl,tabell2);            gl.glColor3d(0, 0, 1);      //z-akse      double[] tabell3 = {0,0,2};      drawAxis(gl,tabell3);   }      private void drawAxis(GL2 gl, double[] tabell){      gl.glBegin(GL.GL_LINES);        gl.glVertex3d(0, 0, 0);        gl.glVertex3dv(tabell, 0);      gl.glEnd();    }   /**     * Called before the OpenGL context is destroyed. Release resource such as buffers.     */   public void dispose(GLAutoDrawable drawable) { }      /** The entry main() method to setup the top-level JFrame with our OpenGL canvas inside */   public static void main(String[] args) {       GLCanvas canvas = new Robot();       canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));              final JFrame frame = new JFrame(); // Swing's JFrame or AWT's Frame       frame.getContentPane().add(canvas);       frame.setTitle(TITLE);       frame.pack();       frame.setVisible(true);               FPSAnimator animator = new FPSAnimator(canvas, 30);       animator.start();   }}/*private class RotateKeyListener extends KeyAdapter{       public void keyPressed(KeyEvent e){           System.out.println("Lytter kalt");           if (e.getKeyChar() == 'l'){            if(gluLookAtZ == 5 && gluLookAtX == 0){                gluLookAtZ = 0;                gluLookAtX = 5;            }            else if(gluLookAtZ == 0 && gluLookAtX == 5){                gluLookAtZ = -5;                gluLookAtX = 0;            }            else if(gluLookAtZ == -5 && gluLookAtX == 0){                gluLookAtZ = 0;                gluLookAtX = -5;            }            else if(gluLookAtZ == 0 && gluLookAtX == -5){                gluLookAtZ = 5;                gluLookAtX = 0;            }           }           Robot.this.repaint();       }   }*/oH 
 i m p o r t   j a v a . a w t . e v e n t . K e y A d a p t e r ; 
 i m p o r t   j a v a . a w t . e v e n t . K e y E v e n t ; 
 i m p o r t   c o m . j o g a m p . o p e n g l . G L ; 
 i m p o r t   j a v a . a w t . * ; 
 i m p o r t   j a v a x . s w i n g . * ; 
 
 i m p o r t   c o m . j o g a m p . o p e n g l . G L 2 ; 
 i m p o r t   c o m . j o g a m p . o p e n g l . g l u . G L U ; 
 i m p o r t   c o m . j o g a m p . o p e n g l . G L A u t o D r a w a b l e ; 
 i m p o r t   c o m . j o g a m p . o p e n g l . G L E v e n t L i s t e n e r ; 
 i m p o r t   c o m . j o g a m p . o p e n g l . a w t . G L C a n v a s ; 
 i m p o r t   c o m . j o g a m p . o p e n g l . u t i l . F P S A n i m a t o r ; 
 i m p o r t   c o m . j o g a m p . o p e n g l . u t i l . g l 2 . G L U T ; 
 
 
 / * * 
   *   N e H e   L e s s o n   # 2   ( J O G L   2   P o r t ) :   B a s i c   2 D   S h a p e s 
   *   @ a u t h o r   T o m a s   H o l t ,   b a s e d   o n   c o d e   f r o m   H o c k - C h u a n   C h u a   ( M a y   2 0 1 2 ) 
   *   @ v e r s i o n   O c t o b e r   2 0 1 6 
   * / 
 
 / *   M a i n   c l a s s   w h i c h   e x t e n d s   G L C a n v a s .   T h i s   m e a n s   t h a t   t h i s   i s   a   O p e n G L   c a n v a s . 
       W e   w i l l   u s e   O p e n G L   c o m m a n d s   t o   d r a w   o n   t h i s   c a n v a s . 
       T h i s   i m p l e m e n t a t i o n   h a s   n o   a n i m a t i o n   o r   u s e r   i n p u t . 
 * / 
 p u b l i c   c l a s s   R o b o t   e x t e n d s   G L C a n v a s   i m p l e m e n t s   G L E v e n t L i s t e n e r   { 
       / /   c o n s t a n t s 
       p r i v a t e   s t a t i c   S t r i n g   T I T L E   =   " H a t e r   J O G L " ; 
       p r i v a t e   s t a t i c   f i n a l   i n t   C A N V A S _ W I D T H   =   3 2 0 ;     / /   w i d t h   o f   t h e   d r a w a b l e 
       p r i v a t e   s t a t i c   f i n a l   i n t   C A N V A S _ H E I G H T   =   2 4 0 ;   / /   h e i g h t   o f   t h e   d r a w a b l e 
     
       / /   S e t u p   O p e n G L   G r a p h i c s   R e n d e r e r   
       p r i v a t e   G L U   g l u ;     / /   f o r   t h e   G L   U t i l i t y 
       p r i v a t e   G L U T   g l u t   =   n e w   G L U T ( ) ; 
       
       / * *   C o n s t r u c t o r   t o   s e t u p   t h e   G U I   f o r   t h i s   C o m p o n e n t   * / 
       p u b l i c   R o b o t ( )   { 
             t h i s . a d d G L E v e n t L i s t e n e r ( t h i s ) ; 
             t h i s . a d d K e y L i s t e n e r ( n e w   R o t a t e K e y L i s t e n e r ( ) ) ; 
       } 
       
 / /   - - - - - -   I m p l e m e n t   m e t h o d s   d e c l a r e d   i n   G L E v e n t L i s t e n e r   ( i n i t , r e s h a p e , d i s p l a y , d i s p o s e )                     
 
       / * * 
         *   C a l l e d   i m m e d i a t e l y   a f t e r   t h e   O p e n G L   c o n t e x t   i s   i n i t i a l i z e d .   C a n   b e   u s e d   
         *   t o   p e r f o r m   o n e - t i m e   i n i t i a l i z a t i o n .   R u n   o n l y   o n c e . 
         * / 
       p u b l i c   v o i d   i n i t ( G L A u t o D r a w a b l e   d r a w a b l e )   { 
             G L 2   g l   =   d r a w a b l e . g e t G L ( ) . g e t G L 2 ( ) ;             / /   g e t   t h e   O p e n G L   g r a p h i c s   c o n t e x t 
             g l u   =   n e w   G L U ( ) ;                                                   / /   g e t   G L   U t i l i t i e s 
             g l . g l C l e a r C o l o r ( 0 . 0 f ,   0 . 0 f ,   0 . 0 f ,   0 . 0 f ) ;   / /   s e t   b a c k g r o u n d   ( c l e a r )   c o l o r 
             g l . g l E n a b l e ( G L 2 . G L _ D E P T H _ T E S T ) ;   / /   e n a b l e s   d e p t h   t e s t i n g 
             g l . g l H i n t ( G L 2 . G L _ P E R S P E C T I V E _ C O R R E C T I O N _ H I N T ,   G L 2 . G L _ N I C E S T ) ;   / /   b e s t   p e r s p e c t i v e   c o r r e c t i o n 
       } 
 
       / * * 
         *   H a n d l e r   f o r   w i n d o w   r e - s i z e   e v e n t .   A l s o   c a l l e d   w h e n   t h e   d r a w a b l e   i s   
         *   f i r s t   s e t   t o   v i s i b l e 
         * / 
       p u b l i c   v o i d   r e s h a p e ( G L A u t o D r a w a b l e   d r a w a b l e ,   i n t   x ,   i n t   y ,   i n t   w i d t h ,   i n t   h e i g h t )   { 
             G L 2   g l   =   d r a w a b l e . g e t G L ( ) . g e t G L 2 ( ) ;     / /   g e t   t h e   O p e n G L   2   g r a p h i c s   c o n t e x t 
 
             i f   ( h e i g h t   = =   0 )   h e i g h t   =   1 ;       / /   p r e v e n t   d i v i d e   b y   z e r o           
             f l o a t   a s p e c t   =   ( f l o a t ) w i d t h   /   h e i g h t ; 
 
             / / S e t   t h e   v i e w   p o r t   ( d i s p l a y   a r e a )   t o   c o v e r   t h e   e n t i r e   w i n d o w 
             / / g l . g l V i e w p o r t ( 0 ,   0 ,   w i d t h / 2 ,   h e i g h t / 2 ) ; 
 
             / /   S e t u p   p e r s p e c t i v e   p r o j e c t i o n ,   w i t h   a s p e c t   r a t i o   m a t c h e s   v i e w p o r t 
             g l . g l M a t r i x M o d e ( G L 2 . G L _ P R O J E C T I O N ) ;     / /   c h o o s e   p r o j e c t i o n   m a t r i x 
             g l . g l L o a d I d e n t i t y ( ) ;                           / /   r e s e t   p r o j e c t i o n   m a t r i x 
             g l u . g l u P e r s p e c t i v e ( 4 5 . 0 ,   a s p e c t ,   0 . 1 ,   1 0 0 . 0 ) ;   / /   f o v y ,   a s p e c t ,   z N e a r ,   z F a r 
 
             / /   E n a b l e   t h e   m o d e l - v i e w   t r a n s f o r m 
             g l . g l M a t r i x M o d e ( G L 2 . G L _ M O D E L V I E W ) ; 
             g l . g l L o a d I d e n t i t y ( ) ;   / /   r e s e t 
       } 
 
       / * * 
         *   C a l l e d   b y   O p e n G L   f o r   d r a w i n g 
         * / 
       p r i v a t e   d o u b l e   v i n k e l 1   =   - 9 0 ; 
       p r i v a t e   d o u b l e   v i n k e l 2   =   9 0 ; 
       p r i v a t e   i n t   f l y t t e B e i n   =   0 ; 
       
       G L U T   k u b e   =   n e w   G L U T ( ) ; 
       
       p u b l i c   v o i d   t e g n B e i n ( G L 2   g l ) { 
             g l . g l T r a n s l a t e f ( - 4 . 5 f ,   - 3 . 5 f ,   0 . 0 f ) ; 
           
             g l . g l P u s h M a t r i x ( ) ; 
             i f   ( v i n k e l 1   <   0 )   v i n k e l 1 - = f l y t t e B e i n ; 
             g l . g l R o t a t e d ( v i n k e l 1 ,   1 ,   0 ,   0 ) ; 
             k u b e . g l u t S o l i d C u b e ( 2 ) ; 
             g l . g l P o p M a t r i x ( ) ; 
             
             g l . g l P u s h M a t r i x ( ) ; 
             i f ( v i n k e l 2   >   0 )   v i n k e l 2 + = f l y t t e B e i n ; 
             g l . g l T r a n s l a t e f ( 3 f ,   0 . 0 f ,   0 . 0 f ) ; 
             g l . g l R o t a t e d ( v i n k e l 2 ,   1 ,   0 ,   0 ) ; 
             k u b e . g l u t S o l i d C u b e ( 2 ) ; 
             g l . g l P o p M a t r i x ( ) ; 
       } 
       
       f l o a t   k o n s t a n t C   =   0 ; 
       f l o a t   k a m e r a v i n k e l   =   0 ; 
       i n t   r   =   - 6 0 ; 
       p r i v a t e   d o u b l e   g l u L o o k A t X   =   j a v a . l a n g . M a t h . c o s ( k a m e r a v i n k e l )   *   r ; 
       p r i v a t e   d o u b l e   g l u L o o k A t Z   =   j a v a . l a n g . M a t h . s i n ( k a m e r a v i n k e l )   *   r ; 
       
       p u b l i c   v o i d   d i s p l a y ( G L A u t o D r a w a b l e   d r a w a b l e )   { 
             G L 2   g l   =   d r a w a b l e . g e t G L ( ) . g e t G L 2 ( ) ;     / /   g e t   t h e   O p e n G L   2   g r a p h i c s   c o n t e x t 
             g l . g l C l e a r ( G L 2 . G L _ C O L O R _ B U F F E R _ B I T   |   G L 2 . G L _ D E P T H _ B U F F E R _ B I T ) ;   / /   c l e a r   c o l o r   a n d   d e p t h   b u f f e r s 
             g l . g l L o a d I d e n t i t y ( ) ;     / /   r e s e t   t h e   m o d e l - v i e w   m a t r i x 
             
               
             g l u L o o k A t X   =   j a v a . l a n g . M a t h . c o s ( k a m e r a v i n k e l )   *   r ; 
             g l u L o o k A t Z   =   j a v a . l a n g . M a t h . s i n ( k a m e r a v i n k e l )   *   r ; 
             g l u . g l u L o o k A t ( g l u L o o k A t X ,   0 ,   g l u L o o k A t Z + k o n s t a n t C ,   0 ,   0 ,   k o n s t a n t C ,   0 ,   1 ,   0 ) ; 
             
             g l . g l T r a n s l a t e f ( 0 f ,   0 . 0 f ,   k o n s t a n t C ) ; 
             
             / / K r o p p 
             g l . g l C o l o r 3 d ( 0 ,   0 ,   1 ) ; 
             k u b e . g l u t S o l i d C u b e ( 5 ) ; 
             
             / / H o d e 
             g l . g l T r a n s l a t e f ( 0 f ,   4 f ,   0 . 0 f ) ; 
             g l . g l C o l o r 3 d ( 1 ,   0 ,   0 ) ; 
             k u b e . g l u t S o l i d C u b e ( 3 ) ; 
             
             / / H � y r e   a r m 
             g l . g l T r a n s l a t e f ( - 3 f ,   - 4 . 0 f ,   0 . 0 f ) ; 
             g l . g l C o l o r 3 d ( 1 ,   1 ,   0 ) ; 
             k u b e . g l u t S o l i d C u b e ( 1 ) ; 
             
             / / V e n s t r e   a r m 
             g l . g l T r a n s l a t e f ( 6 f ,   0 . 0 f ,   0 . 0 f ) ; 
             k u b e . g l u t S o l i d C u b e ( 1 ) ; 
             
             g l . g l C o l o r 3 d ( 0 ,   1 ,   0 ) ; 
             t e g n B e i n ( g l ) ; 
             g l . g l T r a n s l a t e f ( 3 f ,   0 . 0 f ,   0 . 0 f ) ; 
             
             / / v e n s t r e   � y e 
             g l . g l T r a n s l a t e f ( - 1 f ,   8 f ,   2 . 0 f ) ; 
             g l . g l C o l o r 3 d ( 1 ,   1 ,   1 ) ; 
             k u b e . g l u t S o l i d C u b e ( 1 ) ; 
             
             / / h � y r e   � y e 
             g l . g l T r a n s l a t e f ( - 1 . 5 f , 0 , 0 ) ; 
             k u b e . g l u t S o l i d C u b e ( 1 ) ; 
 
       } 
       
       p r i v a t e   c l a s s   R o t a t e K e y L i s t e n e r   e x t e n d s   K e y A d a p t e r { 
               p u b l i c   v o i d   k e y P r e s s e d ( K e y E v e n t   e ) { 
                       S y s t e m . o u t . p r i n t l n ( " L y t t e r   k a l t " ) ; 
                       i f ( e . g e t K e y C h a r ( )   = =   ' d ' ) { 
                           k a m e r a v i n k e l   + =   0 . 1 ; 
                       } 
                       i f ( e . g e t K e y C h a r ( )   = =   ' a ' ) { 
                           k a m e r a v i n k e l   - =   0 . 1 ; 
                         } 
                       i f ( e . g e t K e y C h a r ( )   = =   ' w ' ) { 
                           k o n s t a n t C   + =   5 ; 
                           f l y t t e B e i n   =   5 0 ; 
                       } 
                       i f ( e . g e t K e y C h a r ( )   = =   ' s ' ) { 
                           k o n s t a n t C   + =   - 5 ; 
                           f l y t t e B e i n   =   5 0 ; 
                         } 
                       
                       R o b o t . t h i s . r e p a i n t ( ) ; 
                       
           }           
       } 
       
       p r i v a t e   v o i d   d r a w A l l A x i s ( G L 2   g l ) { 
             g l . g l C o l o r 3 d ( 1 ,   0 ,   0 ) ; 
             
             d o u b l e [ ]   t a b e l l   =   { 2 , 0 , 0 } ; 
             d r a w A x i s ( g l , t a b e l l ) ; 
             
             g l . g l C o l o r 3 d ( 0 ,   1 ,   0 ) ; 
             / / y - a k s e 
             
             d o u b l e [ ]   t a b e l l 2   =   { 0 , 2 , 0 } ; 
             d r a w A x i s ( g l , t a b e l l 2 ) ; 
             
             g l . g l C o l o r 3 d ( 0 ,   0 ,   1 ) ; 
             / / z - a k s e 
 
             d o u b l e [ ]   t a b e l l 3   =   { 0 , 0 , 2 } ; 
             d r a w A x i s ( g l , t a b e l l 3 ) ; 
       } 
       
       p r i v a t e   v o i d   d r a w A x i s ( G L 2   g l ,   d o u b l e [ ]   t a b e l l ) { 
             g l . g l B e g i n ( G L . G L _ L I N E S ) ; 
                 g l . g l V e r t e x 3 d ( 0 ,   0 ,   0 ) ; 
                 g l . g l V e r t e x 3 d v ( t a b e l l ,   0 ) ; 
             g l . g l E n d ( ) ;   
       } 
 
       / * *   
         *   C a l l e d   b e f o r e   t h e   O p e n G L   c o n t e x t   i s   d e s t r o y e d .   R e l e a s e   r e s o u r c e   s u c h   a s   b u f f e r s .   
         * / 
       p u b l i c   v o i d   d i s p o s e ( G L A u t o D r a w a b l e   d r a w a b l e )   {   } 
       
       / * *   T h e   e n t r y   m a i n ( )   m e t h o d   t o   s e t u p   t h e   t o p - l e v e l   J F r a m e   w i t h   o u r   O p e n G L   c a n v a s   i n s i d e   * / 
       p u b l i c   s t a t i c   v o i d   m a i n ( S t r i n g [ ]   a r g s )   { 
               G L C a n v a s   c a n v a s   =   n e w   R o b o t ( ) ; 
               c a n v a s . s e t P r e f e r r e d S i z e ( n e w   D i m e n s i o n ( C A N V A S _ W I D T H ,   C A N V A S _ H E I G H T ) ) ; 
               
               f i n a l   J F r a m e   f r a m e   =   n e w   J F r a m e ( ) ;   / /   S w i n g ' s   J F r a m e   o r   A W T ' s   F r a m e 
               f r a m e . g e t C o n t e n t P a n e ( ) . a d d ( c a n v a s ) ; 
               f r a m e . s e t T i t l e ( T I T L E ) ; 
               f r a m e . p a c k ( ) ; 
               f r a m e . s e t V i s i b l e ( t r u e ) ;   
               
               F P S A n i m a t o r   a n i m a t o r   =   n e w   F P S A n i m a t o r ( c a n v a s ,   3 0 ) ; 
               a n i m a t o r . s t a r t ( ) ; 
       } 
 } 
 
 
 / * 
 
 p r i v a t e   c l a s s   R o t a t e K e y L i s t e n e r   e x t e n d s   K e y A d a p t e r { 
               p u b l i c   v o i d   k e y P r e s s e d ( K e y E v e n t   e ) { 
                       S y s t e m . o u t . p r i n t l n ( " L y t t e r   k a l t " ) ; 
                       i f   ( e . g e t K e y C h a r ( )   = =   ' l ' ) { 
                         i f ( g l u L o o k A t Z   = =   5   & &   g l u L o o k A t X   = =   0 ) { 
                                 g l u L o o k A t Z   =   0 ; 
                                 g l u L o o k A t X   =   5 ; 
                         } 
                         e l s e   i f ( g l u L o o k A t Z   = =   0   & &   g l u L o o k A t X   = =   5 ) { 
                                 g l u L o o k A t Z   =   - 5 ; 
                                 g l u L o o k A t X   =   0 ; 
                         } 
                         e l s e   i f ( g l u L o o k A t Z   = =   - 5   & &   g l u L o o k A t X   = =   0 ) { 
                                 g l u L o o k A t Z   =   0 ; 
                                 g l u L o o k A t X   =   - 5 ; 
                         } 
                         e l s e   i f ( g l u L o o k A t Z   = =   0   & &   g l u L o o k A t X   = =   - 5 ) { 
                                 g l u L o o k A t Z   =   5 ; 
                                 g l u L o o k A t X   =   0 ; 
                         } 
                       } 
                       R o b o t . t h i s . r e p a i n t ( ) ; 
               } 
       } 
 * / 
 
O<� i m p o r t   j a v a . a w t . e v e n t . K e y A d a p t e r ;  i m p o r t   j a v a . a w t . e v e n t . K e y E v e n t ;  i m p o r t   c o m . j o g a m p . o p e n g l . G L ;  i m p o r t   j a v a . a w t . * ;  i m p o r t   j a v a x . s w i n g . * ;   i m p o r t   c o m . j o g a m p . o p e n g l . G L 2 ;  i m p o r t   c o m . j o g a m p . o p e n g l . g l u . G L U ;  i m p o r t   c o m . j o g a m p . o p e n g l . G L A u t o D r a w a b l e ;  i m p o r t   c o m . j o g a m p . o p e n g l . G L E v e n t L i s t e n e r ;  i m p o r t   c o m . j o g a m p . o p e n g l . a w t . G L C a n v a s ;  i m p o r t   c o m . j o g a m p . o p e n g l . u t i l . F P S A n i m a t o r ;  i m p o r t   c o m . j o g a m p . o p e n g l . u t i l . g l 2 . G L U T ;    / * *    *   N e H e   L e s s o n   # 2   ( J O G L   2   P o r t ) :   B a s i c   2 D   S h a p e s    *   @ a u t h o r   T o m a s   H o l t ,   b a s e d   o n   c o d e   f r o m   H o c k - C h u a n   C h u a   ( M a y   2 0 1 2 )    *   @ v e r s i o n   O c t o b e r   2 0 1 6    * /   / *   M a i n   c l a s s   w h i c h   e x t e n d s   G L C a n v a s .   T h i s   m e a n s   t h a t   t h i s   i s   a   O p e n G L   c a n v a s .        W e   w i l l   u s e   O p e n G L   c o m m a n d s   t o   d r a w   o n   t h i s   c a n v a s .        T h i s   i m p l e m e n t a t i o n   h a s   n o   a n i m a t i o n   o r   u s e r   i n p u t .  * /  p u b l i c   c l a s s   R o b o t   e x t e n d s   G L C a n v a s   i m p l e m e n t s   G L E v e n t L i s t e n e r   {        / /   c o n s t a n t s        p r i v a t e   s t a t i c   S t r i n g   T I T L E   =   " H a t e r   J O G L " ;        p r i v a t e   s t a t i c   f i n a l   i n t   C A N V A S _ W I D T H   =   3 2 0 ;     / /   w i d t h   o f   t h e   d r a w a b l e        p r i v a t e   s t a t i c   f i n a l   i n t   C A N V A S _ H E I G H T   =   2 4 0 ;   / /   h e i g h t   o f   t h e   d r a w a b l e             / /   S e t u p   O p e n G L   G r a p h i c s   R e n d e r e r          p r i v a t e   G L U   g l u ;     / /   f o r   t h e   G L   U t i l i t y        p r i v a t e   G L U T   g l u t   =   n e w   G L U T ( ) ;               / * *   C o n s t r u c t o r   t o   s e t u p   t h e   G U I   f o r   t h i s   C o m p o n e n t   * /        p u b l i c   R o b o t ( )   {              t h i s . a d d G L E v e n t L i s t e n e r ( t h i s ) ;              t h i s . a d d K e y L i s t e n e r ( n e w   R o t a t e K e y L i s t e n e r ( ) ) ;        }         / /   - - - - - -   I m p l e m e n t   m e t h o d s   d e c l a r e d   i n   G L E v e n t L i s t e n e r   ( i n i t , r e s h a p e , d i s p l a y , d i s p o s e )                             / * *          *   C a l l e d   i m m e d i a t e l y   a f t e r   t h e   O p e n G L   c o n t e x t   i s   i n i t i a l i z e d .   C a n   b e   u s e d            *   t o   p e r f o r m   o n e - t i m e   i n i t i a l i z a t i o n .   R u n   o n l y   o n c e .          * /        p u b l i c   v o i d   i n i t ( G L A u t o D r a w a b l e   d r a w a b l e )   {              G L 2   g l   =   d r a w a b l e . g e t G L ( ) . g e t G L 2 ( ) ;             / /   g e t   t h e   O p e n G L   g r a p h i c s   c o n t e x t              g l u   =   n e w   G L U ( ) ;                                                   / /   g e t   G L   U t i l i t i e s              g l . g l C l e a r C o l o r ( 0 . 0 f ,   0 . 0 f ,   0 . 0 f ,   0 . 0 f ) ;   / /   s e t   b a c k g r o u n d   ( c l e a r )   c o l o r              g l . g l E n a b l e ( G L 2 . G L _ D E P T H _ T E S T ) ;   / /   e n a b l e s   d e p t h   t e s t i n g              g l . g l H i n t ( G L 2 . G L _ P E R S P E C T I V E _ C O R R E C T I O N _ H I N T ,   G L 2 . G L _ N I C E S T ) ;   / /   b e s t   p e r s p e c t i v e   c o r r e c t i o n        }         / * *          *   H a n d l e r   f o r   w i n d o w   r e - s i z e   e v e n t .   A l s o   c a l l e d   w h e n   t h e   d r a w a b l e   i s            *   f i r s t   s e t   t o   v i s i b l e          * /        p u b l i c   v o i d   r e s h a p e ( G L A u t o D r a w a b l e   d r a w a b l e ,   i n t   x ,   i n t   y ,   i n t   w i d t h ,   i n t   h e i g h t )   {              G L 2   g l   =   d r a w a b l e . g e t G L ( ) . g e t G L 2 ( ) ;     / /   g e t   t h e   O p e n G L   2   g r a p h i c s   c o n t e x t               i f   ( h e i g h t   = =   0 )   h e i g h t   =   1 ;       / /   p r e v e n t   d i v i d e   b y   z e r o                        f l o a t   a s p e c t   =   ( f l o a t ) w i d t h   /   h e i g h t ;               / / S e t   t h e   v i e w   p o r t   ( d i s p l a y   a r e a )   t o   c o v e r   t h e   e n t i r e   w i n d o w              / / g l . g l V i e w p o r t ( 0 ,   0 ,   w i d t h / 2 ,   h e i g h t / 2 ) ;               / /   S e t u p   p e r s p e c t i v e   p r o j e c t i o n ,   w i t h   a s p e c t   r a t i o   m a t c h e s   v i e w p o r t              g l . g l M a t r i x M o d e ( G L 2 . G L _ P R O J E C T I O N ) ;     / /   c h o o s e   p r o j e c t i o n   m a t r i x              g l . g l L o a d I d e n t i t y ( ) ;                           / /   r e s e t   p r o j e c t i o n   m a t r i x              g l u . g l u P e r s p e c t i v e ( 4 5 . 0 ,   a s p e c t ,   0 . 1 ,   1 0 0 . 0 ) ;   / /   f o v y ,   a s p e c t ,   z N e a r ,   z F a r               / /   E n a b l e   t h e   m o d e l - v i e w   t r a n s f o r m              g l . g l M a t r i x M o d e ( G L 2 . G L _ M O D E L V I E W ) ;              g l . g l L o a d I d e n t i t y ( ) ;   / /   r e s e t        }         / * *          *   C a l l e d   b y   O p e n G L   f o r   d r a w i n g          * /        p r i v a t e   d o u b l e   v i n k e l 1   =   - 9 0 ;        p r i v a t e   d o u b l e   v i n k e l 2   =   9 0 ;        p r i v a t e   i n t   f l y t t e B e i n   =   0 ;               G L U T   k u b e   =   n e w   G L U T ( ) ;               p u b l i c   v o i d   t e g n B e i n ( G L 2   g l ) {              g l . g l T r a n s l a t e f ( - 4 . 5 f ,   - 3 . 5 f ,   0 . 0 f ) ;                         g l . g l P u s h M a t r i x ( ) ;              i f   ( v i n k e l 1   <   0 )   v i n k e l 1 - = f l y t t e B e i n ;              g l . g l R o t a t e d ( v i n k e l 1 ,   1 ,   0 ,   0 ) ;              k u b e . g l u t S o l i d C u b e ( 2 ) ;              g l . g l P o p M a t r i x ( ) ;                           g l . g l P u s h M a t r i x ( ) ;              i f ( v i n k e l 2   >   0 )   v i n k e l 2 + = f l y t t e B e i n ;              g l . g l T r a n s l a t e f ( 3 f ,   0 . 0 f ,   0 . 0 f ) ;              g l . g l R o t a t e d ( v i n k e l 2 ,   1 ,   0 ,   0 ) ;              k u b e . g l u t S o l i d C u b e ( 2 ) ;              g l . g l P o p M a t r i x ( ) ;        }               f l o a t   k o n s t a n t C   =   0 ;        f l o a t   k a m e r a v i n k e l   =   0 ;        i n t   r   =   - 6 0 ;        p r i v a t e   d o u b l e   g l u L o o k A t X   =   j a v a . l a n g . M a t h . c o s ( k a m e r a v i n k e l )   *   r ;        p r i v a t e   d o u b l e   g l u L o o k A t Z   =   j a v a . l a n g . M a t h . s i n ( k a m e r a v i n k e l )   *   r ;               p u b l i c   v o i d   d i s p l a y ( G L A u t o D r a w a b l e   d r a w a b l e )   {              G L 2   g l   =   d r a w a b l e . g e t G L ( ) . g e t G L 2 ( ) ;     / /   g e t   t h e   O p e n G L   2   g r a p h i c s   c o n t e x t              g l . g l C l e a r ( G L 2 . G L _ C O L O R _ B U F F E R _ B I T   |   G L 2 . G L _ D E P T H _ B U F F E R _ B I T ) ;   / /   c l e a r   c o l o r   a n d   d e p t h   b u f f e r s              g l . g l L o a d I d e n t i t y ( ) ;     / /   r e s e t   t h e   m o d e l - v i e w   m a t r i x                                          g l u L o o k A t X   =   j a v a . l a n g . M a t h . c o s ( k a m e r a v i n k e l )   *   r ;              g l u L o o k A t Z   =   j a v a . l a n g . M a t h . s i n ( k a m e r a v i n k e l )   *   r ;              g l u . g l u L o o k A t ( g l u L o o k A t X ,   0 ,   g l u L o o k A t Z + k o n s t a n t C ,   0 ,   0 ,   k o n s t a n t C ,   0 ,   1 ,   0 ) ;                           g l . g l T r a n s l a t e f ( 0 f ,   0 . 0 f ,   k o n s t a n t C ) ;                           / / K r o p p              g l . g l C o l o r 3 d ( 0 ,   0 ,   1 ) ;              k u b e . g l u t S o l i d C u b e ( 5 ) ;                           / / H o d e              g l . g l T r a n s l a t e f ( 0 f ,   4 f ,   0 . 0 f ) ;              g l . g l C o l o r 3 d ( 1 ,   0 ,   0 ) ;              k u b e . g l u t S o l i d C u b e ( 3 ) ;                           / / H � y r e   a r m              g l . g l T r a n s l a t e f ( - 3 f ,   - 4 . 0 f ,   0 . 0 f ) ;              g l . g l C o l o r 3 d ( 1 ,   1 ,   0 ) ;              k u b e . g l u t S o l i d C u b e ( 1 ) ;                           / / V e n s t r e   a r m              g l . g l T r a n s l a t e f ( 6 f ,   0 . 0 f ,   0 . 0 f ) ;              k u b e . g l u t S o l i d C u b e ( 1 ) ;                           g l . g l C o l o r 3 d ( 0 ,   1 ,   0 ) ;              t e g n B e i n ( g l ) ;              g l . g l T r a n s l a t e f ( 3 f ,   0 . 0 f ,   0 . 0 f ) ;                           / / v e n s t r e   � y e              g l . g l T r a n s l a t e f ( - 1 f ,   8 f ,   2 . 0 f ) ;              g l . g l C o l o r 3 d ( 1 ,   1 ,   1 ) ;              k u b e . g l u t S o l i d C u b e ( 1 ) ;                           / / h � y r e   � y e              g l . g l T r a n s l a t e f ( - 1 . 5 f , 0 , 0 ) ;              k u b e . g l u t S o l i d C u b e ( 1 ) ;         }               p r i v a t e   c l a s s   R o t a t e K e y L i s t e n e r   e x t e n d s   K e y A d a p t e r {                p u b l i c   v o i d   k e y P r e s s e d ( K e y E v e n t   e ) {                        S y s t e m . o u t . p r i n t l n ( " L y t t e r   k a l t " ) ;                        i f ( e . g e t K e y C h a r ( )   = =   ' d ' ) {                            k a m e r a v i n k e l   + =   0 . 1 ;                        }                        i f ( e . g e t K e y C h a r ( )   = =   ' a ' ) {                            k a m e r a v i n k e l   - =   0 . 1 ;                          }                        i f ( e . g e t K e y C h a r ( )   = =   ' w ' ) {                            k o n s t a n t C   + =   5 ;                            f l y t t e B e i n   =   5 0 ;                        }                        i f ( e . g e t K e y C h a r ( )   = =   ' s ' ) {                            k o n s t a n t C   + =   - 5 ;                            f l y t t e B e i n   =   5 0 ;                          }                                               R o b o t . t h i s . r e p a i n t ( ) ;                                   }                  }               p r i v a t e   v o i d   d r a w A l l A x i s ( G L 2   g l ) {              g l . g l C o l o r 3 d ( 1 ,   0 ,   0 ) ;                           d o u b l e [ ]   t a b e l l   =   { 2 , 0 , 0 } ;              d r a w A x i s ( g l , t a b e l l ) ;                           g l . g l C o l o r 3 d ( 0 ,   1 ,   0 ) ;              / / y - a k s e                           d o u b l e [ ]   t a b e l l 2   =   { 0 , 2 , 0 } ;              d r a w A x i s ( g l , t a b e l l 2 ) ;                           g l . g l C o l o r 3 d ( 0 ,   0 ,   1 ) ;              / / z - a k s e               d o u b l e [ ]   t a b e l l 3   =   { 0 , 0 , 2 } ;              d r a w A x i s ( g l , t a b e l l 3 ) ;        }               p r i v a t e   v o i d   d r a w A x i s ( G L 2   g l ,   d o u b l e [ ]   t a b e l l ) {              g l . g l B e g i n ( G L . G L _ L I N E S ) ;                  g l . g l V e r t e x 3 d ( 0 ,   0 ,   0 ) ;                  g l . g l V e r t e x 3 d v ( t a b e l l ,   0 ) ;              g l . g l E n d ( ) ;          }         / * *            *   C a l l e d   b e f o r e   t h e   O p e n G L   c o n t e x t   i s   d e s t r o y e d .   R e l e a s e   r e s o u r c e   s u c h   a s   b u f f e r s .            * /        p u b l i c   v o i d   d i s p o s e ( G L A u t o D r a w a b l e   d r a w a b l e )   {   }               / * *   T h e   e n t r y   m a i n ( )   m e t h o d   t o   s e t u p   t h e   t o p - l e v e l   J F r a m e   w i t h   o u r   O p e n G L   c a n v a s   i n s i d e   * /        p u b l i c   s t a t i c   v o i d   m a i n ( S t r i n g [ ]   a r g s )   {                G L C a n v a s   c a n v a s   =   n e w   R o b o t ( ) ;                c a n v a s . s e t P r e f e r r e d S i z e ( n e w   D i m e n s i o n ( C A N V A S _ W I D T H ,   C A N V A S _ H E I G H T ) ) ;                               f i n a l   J F r a m e   f r a m e   =   n e w   J F r a m e ( ) ;   / /   S w i n g ' s   J F r a m e   o r   A W T ' s   F r a m e                f r a m e . g e t C o n t e n t P a n e ( ) . a d d ( c a n v a s ) ;                f r a m e . s e t T i t l e ( T I T L E ) ;                f r a m e . p a c k ( ) ;                f r a m e . s e t V i s i b l e ( t r u e ) ;                                 F P S A n i m a t o r   a n i m a t o r   =   n e w   F P S A n i m a t o r ( c a n v a s ,   3 0 ) ;                a n i m a t o r . s t a r t ( ) ;        }  }    / *   p r i v a t e   c l a s s   R o t a t e K e y L i s t e n e r   e x t e n d s   K e y A d a p t e r {                p u b l i c   v o i d   k e y P r e s s e d ( K e y E v e n t   e ) {                        S y s t e m . o u t . p r i n t l n ( " L y t t e r   k a l t " ) ;                        i f   ( e . g e t K e y C h a r ( )   = =   ' l ' ) {                          i f ( g l u L o o k A t Z   = =   5   & &   g l u L o o k A t X   = =   0 ) {                                  g l u L o o k A t Z   =   0 ;                                  g l u L o o k A t X   =   5 ;                          }                          e l s e   i f ( g l u L o o k A t Z   = =   0   & &   g l u L o o k A t X   = =   5 ) {                                  g l u L o o k A t Z   =   - 5 ;                                  g l u L o o k A t X   =   0 ;                          }                          e l s e   i f ( g l u L o o k A t Z   = =   - 5   & &   g l u L o o k A t X   = =   0 ) {                                  g l u L o o k A t Z   =   0 ;                                  g l u L o o k A t X   =   - 5 ;                          }                          e l s e   i f ( g l u L o o k A t Z   = =   0   & &   g l u L o o k A t X   = =   - 5 ) {                                  g l u L o o k A t Z   =   5 ;                                  g l u L o o k A t X   =   0 ;                          }                        }                        R o b o t . t h i s . r e p a i n t ( ) ;                }        }  * /        B [ u�[U             	              ��