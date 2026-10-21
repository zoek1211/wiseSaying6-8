public class WiseSaying {
    // encapsulation
    // always use private for fields to protect the data
    // and provide public getter and setter methods to access and modify the data
    private int id;
    private String content;
    private String author;

    // 기본 생성자
    // WiseSaying 객체를 생성할 때 id, content, author를 설정하지 않고 생성
    public WiseSaying(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
}
