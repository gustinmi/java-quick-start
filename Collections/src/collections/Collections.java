package collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Collections {

    static class BookChapter {

        private final int num;
        private final String letter;
        private final String content;

        public BookChapter(int num, String letter, String content) {
            this.num = num;
            this.letter = letter;
            this.content = content;
        }

        public int getNum() {
            return num;
        }

        public String getLetter() {
            return letter;
        }

        public String getContent() {
            return content;
        }

        @Override
        public String toString() {
            return "BookChapter{" + "num=" + num + ", letter=" + letter + ", content=" + content + '}' + "\n";
        }
    }

    static Comparator<BookChapter> bookChapterComparator = new Comparator<BookChapter>() {

        @Override
        public int compare(BookChapter fruit1, BookChapter fruit2) {
            if ( (fruit1.getLetter()!=null) || (fruit2.getLetter()!=null) ){ // if one has letter
                if (fruit1.getNum() == fruit1.getNum()){ // if numbers are equal
                    return fruit1.getLetter().compareToIgnoreCase(fruit2.getLetter()); // we sort by characters 
                }
            }
            return fruit1.getNum() - fruit1.getNum(); // otherwise just compare numbers
        }

    };

    public static void main(String[] args) {
        List<BookChapter> chapters = new ArrayList<BookChapter>();
        chapters.add(new BookChapter(2, "b", "This is java program."));
        chapters.add(new BookChapter(1, "", "It demonstrates collections."));
        chapters.add(new BookChapter(2, "a", "It utilizes comparator to sort non-trivial example."));
        java.util.Collections.sort( chapters, bookChapterComparator );
        System.out.println(chapters);
    }

}
