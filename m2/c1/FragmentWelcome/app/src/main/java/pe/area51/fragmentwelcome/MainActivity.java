package pe.area51.fragmentwelcome;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginFragmentInterface {

    private HelloFragment helloFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final LoginFragment loginFragment = (LoginFragment) fragmentManager.findFragmentById(R.id.fragment_login);
	// Nuestro Activity implementa la interfaz del fragment, por lo que podemos pasarla como referencia para que capture sus eventos.
        loginFragment.setLoginFragmentInterface(this);
        helloFragment = (HelloFragment) fragmentManager.findFragmentById(R.id.fragment_hello);
    }

    @Override
    public void onLoginButtonClicked(String name) {
	// El argumento del parámetro "name" es pasado por el frament "LoginFragment" ya que nuestro Activity captura el evento.
        helloFragment.showWelcome(name);
    }
}
