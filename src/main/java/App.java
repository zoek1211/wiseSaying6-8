import java.util.Scanner;

public class App {
    Scanner sc = new Scanner(System.in);

    int lastId = 0;

    WiseSaying[] wiseSayings = new WiseSaying[100];
    int wiseSayingsLastIndex = -1;

    // run app 진입점 시작
    void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.println("명령) ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("종료")) {
                break;
            } else if (cmd.equals("등록")) {
                actionWrite();
            } else if (cmd.equals("목록")) {  //목록 출력
                actionlist();
            } else if (cmd.startsWith("삭제")) { // 삭제
                actionDelete(cmd);
            } else if (cmd.startsWith("수정")) { // 수정
                actionModify(cmd);
            }
        }

        sc.close(); // 진입점 끝
    }

    // 명언 입력받음
    private void actionWrite() {
        System.out.print("명언: ");
        String content = sc.nextLine().trim();

        System.out.print("작가: ");
        String author = sc.nextLine().trim();

        WiseSaying wiseSaying = write(content, author);

        System.out.printf("%d번 명언이 등록되었습니다.\n", wiseSaying.id);
    }

    // 내부처리 write
    WiseSaying write(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying();
        wiseSaying.id = ++lastId;
        wiseSaying.content = content;
        wiseSaying.author = author;

        // 명언 배열에 추가하고 wiseSayingsLastIndex를 증가시킴
        wiseSayings[++wiseSayingsLastIndex] = wiseSaying;
        return wiseSaying;
    }

    // 명언 목록 출력
    void actionlist() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------");

        WiseSaying [] forListWiseSayings = findForList();

        for (WiseSaying wiseSaying : forListWiseSayings) {
            System.out.printf("%d / %s / %s\n",
                    wiseSaying.id, wiseSaying.author, wiseSaying.content);
        }
    }

    // 명언 배열의 크기 반환
    int getSize() {

        return wiseSayingsLastIndex + 1;
    }

    // 명언 배열을 역순으로 반환
    WiseSaying[] findForList() {
        WiseSaying[] forListWiseSayings = new WiseSaying[getSize()];

        int forListWiseSayingIndex = -1;

        for (int i = wiseSayingsLastIndex; i >= 0; i--) {
            forListWiseSayings[++forListWiseSayingIndex] = wiseSayings[i];
        }

        return forListWiseSayings;
    }

    // 삭제 명령어 처리
    void actionDelete(String cmd) {
        String[] cmdBits = cmd.split("=", 2); // 삭제 명령어와 id를 분리

        // id가 입력되지 않은 경우 처리
        if  (cmdBits.length < 2 || cmdBits[1].isEmpty()) {
            System.out.println("id를 입력해주세요.");
            return;
        }

        // id를 정수로 변환
        int id = Integer.parseInt(cmdBits[1]);

        int deletedIndex = delete(id);

        // 삭제된 인덱스가 -1이면 해당 id의 명언이 존재하지 않음을 알림
        if (deletedIndex == -1) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        // 삭제 성공 메시지 출력
        System.out.printf("%d번 명언이 삭제되었습니다.\n", id);
    }

    // 삭제 메서드
    int delete(int id) {
        int deleteIndex = findIndexById(id); // id에 해당하는 명언의 인덱스를 찾음

        // 삭제할 명언이 없는 경우 -1 반환
        if (deleteIndex == -1) return deleteIndex;

        // 삭제할 인덱스 이후의 요소들을 한 칸씩 앞으로 이동
        for (int i = deleteIndex + 1; i <= wiseSayingsLastIndex; i++) {
            wiseSayings[i - 1] = wiseSayings[i];
        }

        // 마지막 요소를 null로 설정하고 wiseSayingsLastIndex를 감소시킴
        wiseSayings[wiseSayingsLastIndex] = null;
        wiseSayingsLastIndex--;

        return deleteIndex; // 삭제된 인덱스 반환
    }

    // 수정 명령어 처리
    void actionModify(String cmd) {
        String[] cmdBits = cmd.split("=", 2); // 수정 명령어와 id 분리

        // id가 입력되지 않은 경우 처리
        // cmdBits 배열의 길이가 2보다 작거나, 두 번째 요소가 비어있으면 id가 입력되지 않은 것으로 간주
        if (cmdBits.length < 2 || cmdBits[1].isEmpty()) {
            System.out.println("id를 입력해주세요");
            return;
        }

        int id = Integer.parseInt(cmdBits[1]); // id를 정수로 변환

        // id에 해당하는 명언을 찾음
        WiseSaying wiseSaying = findById(id);

        // id에 해당하는 명언이 없는 경우
        if (wiseSaying == null) {
            System.out.printf("%d번 명언은 존재하지 않습니다.\n", id);
            return;
        }

        // 명언 수정
        System.out.printf("명언(기존) :%s\n", wiseSaying.content);
        System.out.print("명언 : ");
        String content = sc.nextLine().trim();

        System.out.printf("작가(기존) :%s\n", wiseSaying.author);
        System.out.print("작가 : ");
        String author = sc.nextLine().trim();

        modify(wiseSaying, content, author);
    }

    // id에 해당하는 명언의 인덱스를 찾는 메서드
    int findIndexById(int id) {
        for (int i = 0; i <= wiseSayingsLastIndex; i++) {
            if (wiseSayings[i].id == id) {
                return i;
            }
        }
        return -1; // id에 해당하는 명언이 없는 경우 -1 반환
    }

    // id에 해당하는 명언을 찾는 메서드
    // findIndexById 메서드를 사용하여 id에 해당하는 명언의 인덱스를 찾고, 해당 인덱스의 명언을 반환
    WiseSaying findById(int id) {
        int index = findIndexById(id);

        if (index == -1) return null;

        return wiseSayings[index];
    }

    // 명언 수정 메서드
    // WiseSaying 객체의 content와 author를 수정
    void modify(WiseSaying wiseSaying, String content, String author) {
        wiseSaying.content = content;
        wiseSaying.author = author;
    }




}
