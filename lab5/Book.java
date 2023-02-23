public class Book {

    private String author;
    private String title;
    private int year;

    public Book (String author, String title, int year) {
        this.author = author;
        this.title = title;
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public boolean equals(Object other) {
        if(!(other instanceof Book)) {
            return false;
        }

        Book otherBook = (Book) other;

        if ((author == null) && (otherBook.author != null)) {
            return false;
        }
        if ((author != null) && (otherBook.author == null)) {
            return false;
        }
        if ((author != null) && (otherBook.author != null) && (!author.equals(otherBook.author))) {
            return false;
        }
        if ((title == null) && (otherBook.title != null)) {
            return false;
        }
        if ((title != null && otherBook.title == null)) {
            return false;
        }
        if ((title != null) && (otherBook.title != null) && (!title.equals(otherBook.title))) {
            return false;
        }
        if (year != otherBook.year) {
            return false;
        }
        return true;
    }

    public String toString() {
        
        return author + ": " + title + " (" + year + ")";
    }
}
