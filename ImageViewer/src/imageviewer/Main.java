package imageviewer;

import imageviewer.architecture.ImageDisplay;
import imageviewer.architecture.ImageLoader;
import imageviewer.architecture.ImagePresenter;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        ImageLoader loader = new FileImageLoader(new File("images"));        
        MainFrame mainFrame = new MainFrame();        
        ImageDisplay imageDisplay = mainFrame.imageDisplay();
        ImagePresenter.with(loader.load(), imageDisplay);        
        
        mainFrame.setVisible(true);
    }
    
}
