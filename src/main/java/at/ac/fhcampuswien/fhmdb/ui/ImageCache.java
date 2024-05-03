package at.ac.fhcampuswien.fhmdb.ui;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageCache {
    private static final Map<String, Image> cache = new HashMap<>();
    private static final int MAX_CACHE_SIZE = 100; // Adjust as needed

    public static Image getImage(String url) {
        if (cache.containsKey(url)) {
            return cache.get(url);
        } else {
            Image image = new Image(url);
            if (image.isError())
                image = new Image(ImageCache.class.getResourceAsStream("No_image_available.jpg"));
            cacheImage(url, image);
            return image;
        }
    }

    private static void cacheImage(String url, Image image) {
        if (cache.size() >= MAX_CACHE_SIZE) {
            // Perform cache eviction if cache size exceeds threshold
            // You can implement your own cache eviction strategy here
            cache.clear();
        }
        cache.put(url, image);
    }
}
