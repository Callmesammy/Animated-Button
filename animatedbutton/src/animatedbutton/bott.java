
package animatedbutton;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;


public class bott extends JButton{
    
    private int bodersize =3;
    
    // Create Animation 
    private Animator animate;
    private int targetsize;
    private float animatesize;
    private Point pressed;
    private float alpha;
            
    public bott() {
    setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
                   targetsize = Math.max(getWidth(), getHeight())*2;
            pressed = e.getPoint();
           alpha = 0.5f;
           if (animate.isRunning()){
               animate.stop();
           }
           animate.start();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }
        
        
        });
        
             
        TimingTarget target = new TimingTargetAdapter(){
        @Override
        public void timingEvent(float fraction) {
            if (fraction>0.5f){
                alpha = 1- fraction;
            }
            animatesize = fraction * targetsize;
            repaint();
            
        }
        };
        animate = new Animator(900, target);
        
    
         
    
}

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D fd = img.createGraphics();
        fd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //creating of shapes 
        float f[] = new float[] {0f, 0.5f, 1f};
        Color colors[] = new Color[]{ new Color(94, 91, 241), new Color(255, 120, 226), new Color(255, 108, 108)};
        LinearGradientPaint kpk = new LinearGradientPaint(0, 0, width, height, f, colors, MultipleGradientPaint.CycleMethod.REFLECT);
        Shape ck = new Rectangle(0, 0, width, height);
        Shape jc = new Rectangle(bodersize, bodersize, width- bodersize*2, height-bodersize*2);
        Area area = new Area(ck);
        area.subtract(new Area(jc));
        fd.setPaint(kpk);
        fd.fill(area);
        
        // get text messages 
        fd.setFont(getFont());
        FontMetrics g2k=fd.getFontMetrics();
        Rectangle2D g6 = g2k.getStringBounds(getText(), fd);
        double x = (width-g6.getWidth())/2;
        double y = (height-g6.getHeight())/2;
        g.drawString(getText(),(int) x, (int) (y+g2k.getAscent()));

        // creation of the graphic animation 
        if (pressed !=null){
            fd.setColor(Color.WHITE);
            fd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            fd.fillOval((int)(pressed.x-animatesize /2), (int) (pressed.y-animatesize /2), (int)animatesize, (int)animatesize);
        }
        fd.dispose();
        g.drawImage(img, 0, 0, null);
        
    }


    }
    
    

