package gov.va.vba.rbps.lettergeneration.docgen.aimes;

public class Line {
    private final double aimesLinesMarginTop = 71.994;
    private final float thickness = 0.504f;
    private final double marginLeftStart= 40.47;
    private final double marginLeftFinish = 76.47;
    private final double marginTop;

    public Line(double marginTop) {
        this.marginTop = marginTop + aimesLinesMarginTop;
    }

    public Line() {
        this.marginTop = aimesLinesMarginTop;
    }

    public float getThickness() {
        return thickness;
    }

    public double getMarginLeftStart() {
        return marginLeftStart;
    }

    public double getMarginLeftFinish() {
        return marginLeftFinish;
    }

    public double getMarginTop() {
        return marginTop;
    }
}
