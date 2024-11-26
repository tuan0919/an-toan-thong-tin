package Model.Algorithm.Classic;

public record Alphabet(String alphabet) {
    public static final Alphabet EN = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    public static final Alphabet VN = new Alphabet(
            "AĂÂBCDĐEÊGHIKLMNOÔƠPQRSTUƯVXY"
                    + "aăâbcdđeêghiklmnoôơpqrstuưvxy"
                    + "ÁÀẢÃẠẮẰẲẴẶẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆ"
                    + "ÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰ"
                    + "ÝỲỶỸỴ"
                    + "áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệ"
                    + "íìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữự"
                    + "ýỳỷỹỵ");

}
