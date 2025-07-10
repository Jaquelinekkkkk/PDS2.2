package controller;

import dal.ConexaoBD;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Cliente;
import model.LoginDAO;
import model.Usuario;
import util.AlertaUtil;

public class LoginController {

    private Stage stageLogin;
    private Connection conexao;
    private final LoginDAO dao = new LoginDAO();
    private ArrayList<String> listaDados;
    private Usuario user;

    @FXML
    private ImageView imgBancoOnline;
    
    @FXML
    private Button bntFechar;

    @FXML
    private Button bntLogar;

    @FXML
    private Label lblDB;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private TextField txtUsuario;

    @FXML
    void bntFecharClick(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void bntLogarClick(ActionEvent event) throws IOException, SQLException {
        processarLogin();
    }

    public void setStage(Stage stage) {
        this.stageLogin = stage;
    }

    public void verificarBanco() {
//        this.conexao = ConexaoBD.conectar();
//
//        if (this.conexao != null) {
//            System.out.println("Conectou no banco de dados");
//        } else {
//            System.out.println("Problemas na conexão com o banco de dados");
//        }
    
//    if(dao.bancoOnline()){
//        lblDB.setText("Banco de Dados: Online");
//        lblDB.setStyle("-fx-text-fill: blue;");
//    } else {
//        lblDB.setText("Banco de Dados: Offline");
//        lblDB.setStyle("-fx-text-fill: red;");
//    }

       if(dao.bancoOnline()){
           File arquivo = new File("src/main/resources/icones/dbok.png");
           Image imagem = new Image(arquivo.toURI().toString());
           imgBancoOnline.setImage(imagem);
       } else {
           File arquivo = new File("src/main/resources/icones/dberror.png");
           Image imagem = new Image(arquivo.toURI().toString());
           imgBancoOnline.setImage(imagem);
       }

    }

    public void abrirJanela() {
        bntLogar.setDefaultButton(true);
        verificarBanco();
    }

   public void processarLogin() throws IOException, SQLException {
    if (!dao.bancoOnline()) {
        AlertaUtil.mostrarErro("Erro", "Banco de dados desconectado!");
        return;
    }

    String login = txtUsuario.getText().trim();
    String senha = txtSenha.getText().trim();

    if (login.isEmpty() || senha.isEmpty()) {
        AlertaUtil.mostrarErro("Erro", "Preencha login e senha.");
        return;
    }

    // 🎯 Tenta autenticar como usuário do sistema
    user = dao.autenticar(login, senha);
    if (user != null) {
        listaDados = new ArrayList<>();
        listaDados.add(user.getNome());
        listaDados.add(user.getPerfil());

        AlertaUtil.mostrarInformacao("Login aprovado", "Bem-vindo " + user.getNome() + "!");
        if (stageLogin != null) stageLogin.close();
        abrirTelaPrincipal(listaDados);
        return;
    }

    // 🧑‍💼 Se não for usuário, tenta como cliente
    Cliente cliente = dao.autenticarCliente(login, senha);
if (cliente != null) {
    AlertaUtil.mostrarInformacao("Login cliente", "Bem-vindo " + cliente.getNome() + "!");
    if (stageLogin != null) stageLogin.close();
    abrirTelaListagemProduto(cliente); // ✅ agora abre tela de produtos
} else {
    AlertaUtil.mostrarErro("Login inválido", "Usuário ou senha incorretos.");
}

}

    private ArrayList<String> autenticar(String login, String senha) throws SQLException {
        user = dao.autenticar(login, senha);
        if (user != null) {
            ArrayList<String> listaDados = new ArrayList<>();
            listaDados.add(user.getNome());
            listaDados.add(user.getPerfil());
            return listaDados;
        }
        return null;
    }

    private void abrirTelaPrincipal(ArrayList<String> dados) throws MalformedURLException, IOException {
        URL url = new File("src/main/java/view/Principal.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Stage telaPrincipal = new Stage();
        PrincipalController pc = loader.getController();

        pc.setStage(telaPrincipal);

        telaPrincipal.setOnShown(evento -> {
            pc.ajustarElementosJanela(dados);
        });

        Scene scene = new Scene(root);
        
        Image icone = new Image(getClass().getResourceAsStream("/icones/loja.png"));
        telaPrincipal.getIcons().add(icone);

        telaPrincipal.setTitle("Tela principal do Sistema");
        telaPrincipal.setScene(scene);
        telaPrincipal.show();
    }
   private void abrirTelaListagemProduto(Cliente cliente) throws IOException {
    URL url = new File("src/main/java/view/ListagemProduto.fxml").toURI().toURL();
    FXMLLoader loader = new FXMLLoader(url);
    Parent root = loader.load();
    Stage telaProduto = new Stage();

    ListagemProdutosController controller = loader.getController();
    controller.setStage(telaProduto);
    controller.setCliente(cliente); // ✅

    telaProduto.setScene(new Scene(root));
    telaProduto.setTitle("Listagem de Produtos");
    //telaProduto.getIcons().add(new Image(getClass().getResourceAsStream("/icones/produtos.png")));
    telaProduto.show();
}



}
