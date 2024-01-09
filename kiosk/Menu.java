package kiosk;

// 메뉴
public class Menu{
    public String name;    // 카테고리 이름
    public String description; // 카테고리 설명

    // 생성자
    Menu(String name, String description){
        this.name = name;
        this.description = description;
    }
}