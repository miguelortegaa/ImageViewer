package imageviewer;

import imageviewer.architecture.Image;
import imageviewer.architecture.ImageLoader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class FileImageLoader implements ImageLoader {
    private final File[] files;
    private final Map<File, BufferedImage> cache;

    public FileImageLoader(File file) {
        this.files = file.listFiles(FileImageLoader::filterImages);
        this.cache = new HashMap<>();
    }
    
    @Override
    public Image load() {
        return imageAt(0);
    }
    
    private Image imageAt(int i) {
        return new Image() {
            private BufferedImage image = read(files[i]);
            
            @Override
            public Object data() {
                return image;
            }
            
            @Override
            public Image prev() {
                return i > 0 ? imageAt(i-1) : imageAt(files.length-1);
            }

            @Override
            public Image next() {
                return i < files.length-1 ? imageAt(i+1) : imageAt(0);
            }

            @Override
            public int width() {
                return image.getWidth();
            }
            
            @Override
            public int height() {
                return image.getHeight();
            }
            
            private BufferedImage read(File file) {
                if (cache.containsKey(file)) return cache.get(file);
                BufferedImage image = load(file);
                cache.put(file, image);
                return image;
            }
            
            private BufferedImage load(File file) {
                try {
                    return ImageIO.read(file);
                } catch (IOException ex) {
                    return null;
                }
                
            }


        };
    }
    
    
    private static final Set<String> imageExtensions = imageExtensions();
    private static boolean filterImages(File folder, String filename) {
        return imageExtensions.contains(extensionOf(filename));
    }

    private static String extensionOf(String filename) {
        return filename.substring(filename.lastIndexOf('.')+1).toLowerCase();
    }


    private static Set<String> imageExtensions() {
        Set<String> result = new HashSet<String>();
        result.add("png");
        result.add("jpg");
        result.add("jpeg");
        return result;
    }


    
}
