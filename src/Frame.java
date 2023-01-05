import javax.swing.JFrame;

public class Frame extends JFrame{
    Frame(){
        this.add(new Panel());
        this.setTitle("Snack o' Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ? Ensuring Same Experience Across Devices
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        // ? Make Frame Window Appear In The Middle Of The Screen.
        this.setLocationRelativeTo(null);        
    }
}
