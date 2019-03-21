public abstract class Player extends GameObject {

    boolean UpPressed = false;
    boolean DownPressed = false;
    boolean LeftPressed = false;
    boolean RightPressed = false;
    boolean ActionPressed = false;

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleActionPressed() {
        this.ActionPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleActionPressed() {
        this.ActionPressed = false;
    }

}
