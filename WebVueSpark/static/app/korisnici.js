Vue.component('korisnici',{
	data: function(){
		return {
			korisnici:[],
			organizacije: [],
			zaBrisanje: "",
			ime:"",
			email: "",
			prezime: "",
			lozinka: "",
			organizacija: "",
			uloga: ""
			
		}
		
	},
	template: ` 
	<div id = "korisniciSAdmin" style="margin-left: 260px;">
		<h2 class="text-center">Korisnici</h2>
<table class="table table-striped" id = "korisnici">
  <thead>
    <tr>
      <th scope="col">Email</th>
      <th scope="col">Ime</th>
      <th scope="col">Prezime</th>
      <th scope="col">Organizacija</th>
    </tr>
  </thead>
  <tbody>
		<tr v-for="k in korisnici">
			<td >{{k.email}}</td>
			<td >{{k.ime}}</td>
			<td >{{k.prezime}}</td>
			<td >{{k.organizacija}}</td>
			<td>
			<button type="button" v-on:click = "popuni(k)" class="btn btn-primary" data-toggle="modal" data-target="#edit">
				Izmeni
			</button>
			</td>
			<td>
			<button type="button" v-on:click = "obrisi(k.email)" class="btn btn-primary">
				Obrisi
			</button>
			</td>
		</tr>
  </tbody>
</table>
<!-- Button trigger modal -->
<button id = "Dodaj" v-on:click = "isprazniPolja()" type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalDodaj">
  Dodaj korisnika
</button>

<!-- Modal -->
<div class="modal fade" id="modalDodaj" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Dodavanje korisnika</h5>
        <button type="button"  class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id = "dodaj">  
       		<div class="form-group">
          		Email: <input name = "email"  v-model ="email" type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Lozinka: <input name = "lozinka"  v-model ="lozinka" type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
          		Ime: <input name = "ime" type="text"  v-model ="ime" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Prezime: <input name = "prezime"type="text"  v-model ="prezime" class="form-control" required="required">
       		</div>
       		<div class="form-group" id = "organizacija">
       			<label for = "organizacija">Oganizacija: </label>
				<select id = "organizacija"  class= "form-control"  v-model ="organizacija" required>
					<option v-for="o in organizacije" :value="o.ime">{{o.ime}}</option>
				</select>
		    </div>
       		<div class="form-group">
           		Uloga: <select name = "tip"  v-model ="uloga" class="form-control" id="exampleFormControlSelect1">
      				<option>ADMIN</option>
					<option>KORISNIK</option>
				</select>
       		</div>
       		<div class="form-group">
           		<button  v-on:click.prevent = "dodaj()" class="btn btn-primary btn-block">Dodaj</button>
           		
       		</div>   
        </form>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="edit" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Izmena korisnika</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id = "izmeni">  
       		<div class="form-group">
          		Email: <input name = "email" v-model ="email" disabled type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Lozinka: <input name = "lozinka" v-model ="lozinka" type="text" class="form-control" required="required" >
       		</div>
       		<div class="form-group">
          		Ime: <input name = "ime" v-model ="ime" type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Prezime: <input name = "prezime" v-model ="prezime" type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Organizacija<input name = "organizacija" v-model ="organizacija" disabled type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Uloga: <select name = "tip" v-model ="uloga" class="form-control" id="exampleFormControlSelect1">
      				<option value ="ADMIN">Admin</option>
					<option value ="KORISNIK">Korisnik</option>
				</select>
       		</div>
       		<div class="form-group">
           		<button  v-on:click.prevent = "izmeni()" class="btn btn-primary btn-block">Izmeni</button>
       		</div>   
        </form>
      </div>
    </div>
  </div>
</div>
	</div>
`
	, 
	methods : {
        popuni(k) {
        	this.ime = k.ime;
        	this.prezime = k.prezime;
        	this.organizacija = k.organizacija;
        	this.lozinka = k.lozinka;
        	this.email = k.email;
        	this.uloga = k.uloga;
        },
        isprazniPolja() {
        	this.ime = "";
        	this.prezime = "";
        	this.organizacija ="";
        	this.lozinka ="";
        	this.email = "";
        	this.uloga = "";
        },
        dodaj() {
        	axios
		    .post('rest/addKorisnik', { "email": this.email, "lozinka": this.lozinka, "ime": this.ime, "prezime": this.prezime,"organizacija": this.organizacija, "uloga": this.uloga})
		    .then((response) => {
		    	console.log(response.data);
		    	$('#modalDodaj').modal('hide');
            	$('.modal-backdrop').remove();
		    	axios
			    .get('rest/getSviKorisnici')
			    .then((response) => {
			    	console.log(response.data.korisnici);
			    	this.korisnici = response.data.korisnici;
			    });
		    });
        },
        izmeni() {
        	axios
		    .post('rest/izmeniKorisnik',{"email": this.email,"lozinka": this.lozinka,"ime": this.ime,"prezime": this.prezime,"organizacija": this.organizacija,"uloga": this.uloga})
		    .then((response) => {
		    	$('#edit').modal('hide');
            	$('.modal-backdrop').remove();
		    	console.log("ok");
		    	console.log(response.data);
		    	axios
			    .get('rest/getSviKorisnici')
			    .then((response) => {
			    	console.log(response.data.korisnici);
			    	this.korisnici = response.data.korisnici;
			    });
		    });
        },
        obrisi(email){
        	console.log(email);
        	axios
		    .get('rest/obrisiKorisnika/' + email )
		    .then((response) => {
		    	console.log(response.data);
		    	axios
			    .get('rest/getSviKorisnici')
			    .then((response) => {
			    	console.log(response.data.korisnici);
			    	this.korisnici = response.data.korisnici;
			    });
		    });
        }
        
        
	},
	mounted() {
		axios
	    .get('rest/login/getUser')
	    .then((response) => {
	    	console.log(response.data);
	    	axios
		    .get('rest/getSviKorisnici')
		    .then((response) => {
		    	console.log(response.data.korisnici);
		    	this.korisnici = response.data.korisnici;
		    });
	    	axios
		    .get('rest/getSveOrganizacije')
		    .then((response) => {
		    	console.log(response.data);
		    	this.organizacije = response.data.organizacije;
		    });
	    })
	    .catch(response => {
			this.$router.push("/");
		});
	}
});