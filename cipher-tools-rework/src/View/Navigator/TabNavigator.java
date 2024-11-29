package View.Navigator;

import Controller.*;
import View.*;

import javax.swing.*;

public class TabNavigator extends JTabbedPane {
    private ClassicScreen_View TraditionalScreenView = new ClassicScreen_View();
    {
        new ClassicScreen_Controller(TraditionalScreenView);
    }
    private SymmetricScreen_View SymmetricScreenView = new SymmetricScreen_View();
    {
        new SymmetricScreen_Controller(SymmetricScreenView);
    }
    private AsymmetricScreen_View AsymmetricScreenView = new AsymmetricScreen_View();
    {
        new AsymmetricScreen_Controller(AsymmetricScreenView);
    }
    private HashScreen_View HashScreenView = new HashScreen_View();
    {
        new HashScreen_Controller(HashScreenView);
    }
    private SignatureScreen_View SignatureScreenView = new SignatureScreen_View();
    {
        new SignatureScreen_Controller(SignatureScreenView);
    }

    public TabNavigator() {
        addTab("Classic", TraditionalScreenView);
        addTab("Symmetric", SymmetricScreenView);
        addTab("Asymmetric", AsymmetricScreenView);
        addTab("Hash", HashScreenView);
        addTab("Signature", SignatureScreenView);
        setSelectedIndex(1); // Set the initial tab to Traditional Screen by default
    }
}
