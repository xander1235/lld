import java.util.ArrayList;
import java.util.List;

// Audience class representing the target audience for an advertisement
class Audience {
    private int minAge;
    private int maxAge;
    private Gender gender;
    private String location;

    public Audience(int minAge, int maxAge, Gender gender, String location) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.gender = gender;
        this.location = location;
    }
}

// Enum for gender
enum Gender {
    MALE,
    FEMALE,
    OTHER
}

// Advertisement class representing an advertisement
class Advertisement {
    private String title;
    private int duration;
    private Audience targetAudience;
    private AdvertisementContentStrategy contentStrategy;
    private List<AdvertisementObserver> observers;

    public Advertisement(String title, int duration, Audience targetAudience, AdvertisementContentStrategy contentStrategy) {
        this.title = title;
        this.duration = duration;
        this.targetAudience = targetAudience;
        this.contentStrategy = contentStrategy;
        this.observers = new ArrayList<>();
        notifyObservers();
    }

    public void addObserver(AdvertisementObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(AdvertisementObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (AdvertisementObserver observer : observers) {
            observer.update(this);
        }
    }

    public void displayContent() {
        contentStrategy.displayContent();
    }

    public String getTitle() {
        return title;
    }
}

// Interface for the advertisement content strategy
interface AdvertisementContentStrategy {
    void displayContent();
}

// Concrete implementation of AdvertisementContentStrategy for text content
class TextContentStrategy implements AdvertisementContentStrategy {
    private String text;

    public TextContentStrategy(String text) {
        this.text = text;
    }

    public void displayContent() {
        System.out.println("Text: " + text);
    }
}

// Concrete implementation of AdvertisementContentStrategy for image content
class ImageContentStrategy implements AdvertisementContentStrategy {
    private String imageUrl;

    public ImageContentStrategy(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void displayContent() {
        System.out.println("Image URL: " + imageUrl);
    }
}

// Concrete implementation of AdvertisementContentStrategy for video content
class VideoContentStrategy implements AdvertisementContentStrategy {
    private String videoUrl;

    public VideoContentStrategy(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void displayContent() {
        System.out.println("Video URL: " + videoUrl);
    }
}

// Factory class for creating different types of advertisements
class AdvertisementFactory {
    public static Advertisement createAdvertisement(String title, int duration, Audience targetAudience, String contentType, String content) {
        AdvertisementContentStrategy contentStrategy;
        if (contentType.equals("text")) {
            contentStrategy = new TextContentStrategy(content);
        } else if (contentType.equals("image")) {
            contentStrategy = new ImageContentStrategy(content);
        } else if (contentType.equals("video")) {
            contentStrategy = new VideoContentStrategy(content);
        } else {
            throw new IllegalArgumentException("Invalid content type: " + contentType);
        }

        Advertisement advertisement = new Advertisement(title, duration, targetAudience, contentStrategy);
        return advertisement;
    }
}

// Observer interface for subscribing to advertisements
interface AdvertisementObserver {
    void update(Advertisement advertisement);
}

// Concrete implementation of AdvertisementObserver
class User implements AdvertisementObserver {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public void update(Advertisement advertisement) {
        System.out.println(name + " received a new advertisement: " + advertisement.getTitle());
    }
}

// Composite class representing a campaign consisting of multiple advertisements
class Campaign extends Advertisement {
    private List<Advertisement> advertisements;

    public Campaign(String title, int duration, Audience targetAudience) {
        super(title, duration, targetAudience, null);
        this.advertisements = new ArrayList<>();
    }

    public void addAdvertisement(Advertisement advertisement) {
        advertisements.add(advertisement);
    }

    public void removeAdvertisement(Advertisement advertisement) {
        advertisements.remove(advertisement);
    }

    @Override
    public void displayContent() {
        System.out.println("Campaign: " + getTitle());
        for (Advertisement advertisement : advertisements) {
            advertisement.displayContent();
        }
    }
}

// Client class that uses the AdvertisementFactory, Observer, and Composite Pattern
public class Main {
    public static void main(String[] args) {
        Audience audience = new Audience(18, 40, Gender.MALE, "New York");
        Advertisement advertisement1 = AdvertisementFactory.createAdvertisement("Ad1", 10, audience, "text", "Special offer!");
        Advertisement advertisement2 = AdvertisementFactory.createAdvertisement("Ad2", 15, audience, "image", "https://example.com/image.jpg");
        Advertisement advertisement3 = AdvertisementFactory.createAdvertisement("Ad3", 20, audience, "video", "https://example.com/video.mp4");

        parser.User user1 = new parser.User("User1");
        parser.User user2 = new parser.User("User2");

        advertisement1.addObserver(user1);
        advertisement2.addObserver(user2);
        advertisement3.addObserver(user1);

        Campaign campaign = new Campaign("Summer Sale", 30, audience);
        campaign.addAdvertisement(advertisement1);
        campaign.addAdvertisement(advertisement2);
        campaign.addAdvertisement(advertisement3);

        advertisement1.displayContent();
        advertisement2.displayContent();
        advertisement3.displayContent();

        campaign.displayContent();
    }
}

/**
 * Factory Pattern:
 *
 * The AdvertisementFactory class represents the Factory pattern.
 * It encapsulates the creation logic for different types of advertisements based on specific criteria.
 * The factory method createAdvertisement() creates and returns different types of advertisements based on the provided parameters.
 * This pattern helps to separate the advertisement creation logic from the client code and provides a centralized place for creating advertisements.
 * Strategy Pattern:
 *
 * The AdvertisementContentStrategy interface represents the Strategy pattern.
 * It defines a family of algorithms (content strategies) that can be used interchangeably.
 * The concrete implementations (TextContentStrategy, ImageContentStrategy, VideoContentStrategy) encapsulate the different strategies for displaying advertisement content.
 * The Advertisement class has a reference to the AdvertisementContentStrategy interface, and the displayContent() method delegates the display operation to the current strategy.
 * This pattern allows the flexibility to change the content strategy at runtime and supports the principle of encapsulating varying behavior.
 * Observer Pattern:
 *
 * The AdvertisementObserver interface and parser.User class represent the Observer pattern.
 * The Advertisement class maintains a list of observers and provides methods to subscribe, unsubscribe, and notify observers.
 * The parser.User class implements the AdvertisementObserver interface and defines the behavior to be executed when an advertisement is updated.
 * When a new advertisement is created, the notifyObservers() method is called, which notifies all subscribed observers.
 * This pattern enables loose coupling between the advertisements and the observers, allowing multiple observers to react to advertisement updates independently.
 * Composite Pattern:
 *
 * The Campaign class represents the Composite pattern.
 * It allows grouping multiple advertisements together as a single campaign.
 * The Campaign class extends the Advertisement class and maintains a list of individual advertisements.
 * The displayContent() method is overridden to display the campaign title and delegate the display operation to each individual advertisement in the campaign.
 * This pattern enables treating both individual advertisements and campaigns in a uniform way, simplifying the management of grouped advertisements.
 * By utilizing these design patterns, the code achieves better modularity, flexibility, extensibility, and maintainability. It separates concerns, promotes code reusability, and provides a clear structure for creating, displaying, and managing different types of advertisements.
 */
