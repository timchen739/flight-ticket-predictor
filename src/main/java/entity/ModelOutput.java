package entity;

public class ModelOutput {
    private double currentPrice;
    private double lowestPrice;
    private String advice;

    public static class Builder {
        private double currentPrice;
        private double lowestPrice;
        private String advice;

        public Builder currentPrice(double currentPrice) {
            this.currentPrice = currentPrice;
            return this;
        }

        public Builder lowestPrice(double lowestPrice) {
            this.lowestPrice = lowestPrice;
            return this;
        }

        public Builder advice(String advice) {
            this.advice = advice;
            return this;
        }

        public ModelOutput build() {
            return new ModelOutput(this);
        }
    }

    private ModelOutput(Builder builder) {
        this.currentPrice = builder.currentPrice;
        this.lowestPrice = builder.lowestPrice;
        this.advice = builder.advice;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }
}
