public class TestMeMain implements ISupplier {
    public static void main(String[] args) {
        TestMe obj = new TestMe();
        TestMe.Inner o = obj.new Inner();

        TestMeKotlin oo = new TestMeKotlin("apple"); // , "mango");

        System.out.println(oo.getB());
    }

    public void callMeMayBe() {
        System.out.println("Call!!");
    }

    @Override
    public void supply() {

    }
}
