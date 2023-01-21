package imageviewer.architecture;

public interface Image {
    
    Object data();
    int width();
    int height();
    Image prev();
    Image next();


    
}
