import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 04/21/14
 * Time: 05:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageButton extends JButton {
    private Image image, imageHover;
    private boolean hover, click, isColor = false;
    private Color color, colorHover, colorBorder = Color.BLACK;
    private int index = 0;

    public ImageButton(URL d, URL h) {
        try {
            image = ImageIO.read(d);
            imageHover = ImageIO.read(h);
            this.hover = false;
            click = false;
            customUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageButton(URL d) {
        try {
            image = ImageIO.read(d);
            imageHover = image;
            hover = false;
            click = false;
            customUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageButton(String s, URL d) {
        super(s);
        try {
            image = ImageIO.read(d);
            imageHover = image;
            hover = false;
            click = false;
            customUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageButton(Image image, Image imageHover) {
        this.image = image;
        this.imageHover = imageHover;
        this.hover = false;
        click = false;
        customUI();
    }

    public ImageButton(Image i) {
        image = i;
        imageHover = null;
        hover = false;
        click = false;
        customUI();
    }

    public ImageButton(String l) {
        this.image = null;
        this.imageHover = null;
        this.hover = false;
        click = false;
        setText(l);
        setEnabled(false);
        customUI();
    }

    public ImageButton(String t, Color c, Color h) {
        super(t);
        color = c;
        colorHover = h;
        hover = false;
        click = false;
        customUI();
    }

    public ImageButton(String s, Color c, Color h, Color b) {
        super(s);
        color = c;
        colorHover = h;
        colorBorder = b;
        hover = false;
        click = false;
        customUI();
    }

    public void setIndex(int i){
        index = i;
    }

    public int getIndex(){
        return index;
    }

    public Image getImage(){
        return image;
    }

    public Image getImage(int w, int h){
        return image.getScaledInstance(h, w, Image.SCALE_DEFAULT);
    }

    private void customUI() {
        setBorder(new CompoundBorder(new LineBorder(colorBorder), new EmptyBorder(20, 20, 20, 20)));
        setFocusable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                if(imageHover == null)
                    hover = false;
                if(colorHover == null)
                    hover = false;
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                hover = false;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                click = true;
                hover = true;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        if (isEnabled()) {
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            if (hover) {
                if (imageHover != null) {
                    g2.drawImage(imageHover.getScaledInstance(getHeight(), getWidth(), Image.SCALE_DEFAULT), 0, 0, getWidth(), getHeight(), this);
                    g2.drawString(getText(), getWidth()-15, getHeight() -10);
                }
                if (colorHover != null) {
                    setBackground(colorHover);
                    setForeground(color);
                    super.paintComponent(g);
                }
            } else {
                if (color != null) {
                    setBackground(color);
                    setForeground(colorHover);
                    super.paintComponent(g);
                } else {
                    g2.drawImage(image.getScaledInstance(getHeight(), getWidth(), Image.SCALE_DEFAULT), 0, 0, getWidth(), getHeight(), this);
                    g2.drawString(getText(), getWidth()-15, getHeight() -10);
                }
            }
            g2.dispose();
        } else {
            if (imageHover != null)
                g2.drawImage(imageHover.getScaledInstance(getHeight(), getWidth(), Image.SCALE_DEFAULT), 0, 0, getWidth(), getHeight(), this);
            else if (colorHover != null) {
                setBackground(colorHover);
                setForeground(color);
                super.paintComponent(g);
            }else
                super.paintComponent(g);
        }

    }

}
