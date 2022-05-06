package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Dados;

public class Inserir {
    private PreparedStatement realizarQuery;
    private Connection conectar;
    public Inserir(){
        conectar=new Conexao().conectar();
    }
    public Boolean inserir(Dados dados){
        Boolean retorno = false;
        try{        
            String sqlImagem = "INSERT INTO imagens (imagem,tipo,titulo,classificacaoIndicativa,descricao) VALUES (?,?,?,?,?)";
            realizarQuery=conectar.prepareStatement(sqlImagem);
            realizarQuery.setBytes(1, dados.getImagem());
            realizarQuery.setString(2, dados.getTipo());
            realizarQuery.setString(3, dados.getTitulo());
            realizarQuery.setInt(4, dados.getClassificacao());
            realizarQuery.setString(5, dados.getComentario());
            realizarQuery.executeUpdate();
            String sqlAudio="INSERT INTO audio (som,comentario,idDaImagem) VALUES (?,?,(SELECT MAX(idImagem) FROM imagens))";
            realizarQuery=conectar.prepareStatement(sqlAudio);
            realizarQuery.setBytes(1, dados.getAudio());
            realizarQuery.setString(2, dados.getcomentarioDoAudio());
            realizarQuery.executeUpdate();
          if(dados.getIdImagemVideo()==0){
            String sqlVideos="INSERT INTO videos (video,videoEstilizado,idImagem) VALUES (?,?,(SELECT MAX(idImagem) FROM imagens))";
            realizarQuery=conectar.prepareStatement(sqlVideos);
            realizarQuery.setBytes(1,dados.getVideos());
            realizarQuery.setString(2,dados.getUrl());
            realizarQuery.execute();
            realizarQuery.close();
            retorno = true;
          }else{
            String sqlVideos="INSERT INTO videos (video,videoEstilizado,idImagem) VALUES (?,?,?)";
            realizarQuery=conectar.prepareStatement(sqlVideos);
            realizarQuery.setBytes(1,dados.getVideos());
            realizarQuery.setString(2,dados.getUrl());
            realizarQuery.setInt(3,dados.getIdImagemVideo());
            realizarQuery.execute();
            realizarQuery.close();
            retorno = true;
          }
        }
        catch(SQLException ex){
        }
        
        return retorno;
    
    }
   
}
