package persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import entity.Empresa;
import entity.Endereco;

public class EmpresaDao {
	
	EntityManager em;
	
	public EmpresaDao() {
		//em = Persistence.createEntityManagerFactory("bd_venda").createEntityManager();
	}
	
	public void create(Empresa emp) throws Exception{
		System.out.println(emp);
		/*em.getTransaction().begin();
			em.persist(emp);
		em.getTransaction().commit();*/
	}
	
	public List<Empresa> findAll(){
		//return em.createQuery("from Empresa").getResultList();
		List<Empresa> empresas = new ArrayList<>();
		
		Endereco endereco = new Endereco(1, "Rua Camarista Méier 831", "Centro", "Rio de Janeiro", "RJ", new Double("-22.9084852"), new Double("-43.2994588"), null);
		Empresa empresa = new Empresa(1, "VENANCIO PARK", endereco );
		
		Endereco endereco2 = new Endereco(2, "Rua Camarista Méier 831", "Centro", "Rio de Janeiro", "RJ", new Double("-22.9076528"), new Double("-43.2981686"), null);
		Empresa empresa2 = new Empresa(2, "GARAGEM CATULO", endereco2 );

		Endereco endereco3 = new Endereco(3, "Rua Camarista Méier 831", "Centro", "Rio de Janeiro", "RJ", new Double("-22.9066323"), new Double("-43.3070232"), null);
		Empresa empresa3 = new Empresa(3, "VIOLETA PARK", endereco3 );
		
		Endereco endereco4 = new Endereco(4, "Rua Camarista Méier 831", "Centro", "Rio de Janeiro", "RJ", new Double("-22.912221"), new Double("-43.2940418"), null);
		Empresa empresa4 = new Empresa(4, "MARANHÃO ESTACIONAMENTO", endereco4 );
		
		empresas.add(empresa);	
		empresas.add(empresa2);
		empresas.add(empresa3);
		empresas.add(empresa4);
		return empresas;
	}
	
	
	
}
