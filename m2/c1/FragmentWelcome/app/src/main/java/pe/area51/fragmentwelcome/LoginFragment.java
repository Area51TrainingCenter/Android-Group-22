package pe.area51.fragmentwelcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment {

    private EditText nameEditText;
    private Button loginButton;

    private LoginFragmentInterface loginFragmentInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        nameEditText = (EditText) view.findViewById(R.id.edittext_name);
        loginButton = (Button) view.findViewById(R.id.button_login);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginFragmentInterface != null) {
                    final String name = nameEditText.getText().toString();
                    loginFragmentInterface.onLoginButtonClicked(name);
                }
            }
        });
    }

    public void setLoginFragmentInterface(LoginFragmentInterface loginFragmentInterface) {
        this.loginFragmentInterface = loginFragmentInterface;
    }

    public interface LoginFragmentInterface {

        public void onLoginButtonClicked(final String name);

    }
}
