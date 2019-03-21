package GameObjects;

public abstract class Player extends GameObject {

    boolean UpPressed = false;
    boolean DownPressed = false;
    boolean LeftPressed = false;
    boolean RightPressed = false;
    boolean ActionPressed = false;

    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleActionPressed() {
        this.ActionPressed = true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleActionPressed() {
        this.ActionPressed = false;
    }

}
