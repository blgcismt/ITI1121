import java.util.Comparator;

public class BookComparator implements Comparator<Book> {

    public int compare(Book first, Book second) {
            
            if ((first.getAuthor() == null) && (second.getAuthor() == null)) {
                return 0;
            }
    
            if (first.getAuthor() == null) {
                return -1;
            }
    
            if (second.getAuthor() == null) {
                return 1;
            }
    
            int authorCompare = first.getAuthor().compareTo(second.getAuthor());
    
            if (authorCompare != 0) {
                return authorCompare;
            }
    
            if ((first.getTitle() == null) && (second.getTitle() == null)) {
                return 0;
            }
    
            if (first.getTitle() == null) {
                return -1;
            }
    
            if (second.getTitle() == null) {
                return 1;
            }
    
            int titleCompare = first.getTitle().compareTo(second.getTitle());
    
            if (titleCompare != 0) {
                return titleCompare;
            }
    
            if (first.getYear() == second.getYear()) {
                return 0;
            }
    
            if (first.getYear() < second.getYear()) {
                return -1;
            }
    
            return 1;

    }

}
