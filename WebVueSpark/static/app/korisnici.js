Vue.component('korisnici',{
	data: function(){
		return {
			korisnici:[],
		}
		
	},
	template: ` 
	<div id = "korisniciSAdmin">
		<p>Korisnici</p>
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
  </tbody>
</table>
<!-- Button trigger modal -->
<button id = "Dodaj" type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalDodaj">
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
          		Email: <input name = "email" type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Lozinka: <input name = "lozinka"type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
          		Ime: <input name = "ime" type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Prezime: <input name = "prezime"type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group" id = "organizacija">
           		Organizacija<input name = "organizacija"type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Tip<select name = "tip" class="form-control" id="exampleFormControlSelect1">
      				<option>ADMIN</option>
					<option>KORISNIK</option>
				</select>
       		</div>
       		<div class="form-group">
           		<button type="submit" class="btn btn-primary btn-block">Dodaj</button>
           		
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
          		Email: <input name = "email" disabled type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Lozinka: <input name = "lozinka"type="text" class="form-control" required="required" >
       		</div>
       		<div class="form-group">
          		Ime: <input name = "ime" type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Prezime: <input name = "prezime"type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Organizacija<input name = "organizacija" disabled type="text" class="form-control" required="required">
       		</div>
       		<div class="form-group">
           		Tip<select name = "tip" class="form-control" id="exampleFormControlSelect1">
      				<option>ADMIN</option>
					<option>KORISNIK</option>
				</select>
       		</div>
       		<div class="form-group">
           		<button type="submit" class="btn btn-primary btn-block">Izmeni</button>
       		</div>   
        </form>
        <button id= "btnObrisi" class="btn btn-primary btn-block">Obrisi</button>
      </div>
    </div>
  </div>
</div>
	</div>
`
	, 
	methods : {
		odjava() {
        	axios
        	.get('rest/login/odjava')
			.then(response => this.$router.replace({ name: "login" }));
        }
	},
	mounted() {
		axios
	    .get('rest/login/getUser')
	    .then((response) => {
	    	console.log(response.data);	
	    })
	    .catch(response => {
			this.$router.push("/");
		});
	}
});