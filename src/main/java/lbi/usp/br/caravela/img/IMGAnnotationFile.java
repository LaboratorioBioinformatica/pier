package lbi.usp.br.caravela.img;

public interface IMGAnnotationFile {

    int getGeneIdLocation();

    int getTypeIdLocation();

    int getIdentityLocation();

    int getAlignLengthLocation();

    int getQueryStartLocation();

    int getQueryEndLocation();

    int getSubjectStartLocation();

    int getSubjectEndLocation();

    int getEvalueLocation();

    int getBitScoreLocation();

}
