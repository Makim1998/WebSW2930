const Korisnici = { template: '<korisnici></korisnici>' }
const Organizacije = { template: '<organizacije></organizacije>' }
const Kategorije = {template: '<kategorije></kategorije>'}
const Diskovi = {template: '<diskovi></diskovi>'}
const VM = {template: '<vm></vm>'}
const Profil = {template: '<profil></profil>'}
const Empty = {template: '<blank></blank>'}

Vue.component('homepage',{
	data: function(){
		return {
			component:"blank",
		}
		
	},
	template: ` 
	<div id = "Home">
		<div  id="mySidenav" class="sidenavbar">
	      	<a href = "#korisnici"v-on:click = "component = 'korisnici'"  >Korisnici</a>
	      	<a href = "#organizacije" v-on:click = "component = 'organizacije'"  >Organizacije</a>
			<a href = "#kategorije" v-on:click = "component = 'kategorije'">Kategorije</a>
			<a href = "#diskovi" v-on:click = "component = 'diskovi'">Diskovi</a>
			<a href = "#vm" v-on:click = "component = 'vm'" >Virtualne masine</a>
			<a href = "#profil" v-on:click = "component = 'profil'" >Profil</a>
			<div class="align-self-center mx-auto"> 
                <button id = "odjavi" class="btn btn-primary btn-sm" v-on:click="odjava()">Odjavi se</button>
            </div> 
			
		</div>
		<!-- /#sidebar-wrapper -->
	     <div id="page-content-wrapper" >
	     	<div v-if="component === 'korisnici'">
			  <korisnici></korisnici>
			</div>
			<div v-else-if="component === 'organizacije'">
			  <organizacije></organizacije>
			</div>
			<div v-else-if="component === 'kategorije'">
			  <kategorije></kategorije>
			</div>
			
			<div v-else-if="component === 'diskovi'">
			  <diskovi></diskovi>
			</div>
			<div v-else-if="component === 'vm'">
			  <vm></vm>
			</div>
			<div v-else-if="component === 'profil'">
			  <profil></profil>
			</div>
			<div v-else>
			  <blank></blank>
			</div>
			
	    </div>
	    <!-- /#page-content-wrapper -->
	    <div class ="footer"></div>
	</div>
`
	, 
	components:{
		'korisnici': Korisnici,
		'organizacije': Organizacije,
		'kategorije': Kategorije,
		'diskovi': Diskovi,
		'vm': VM,
		'blank':Empty,
		'profil': Profil
	},
	
	methods : {
		odjava() {
        	axios
        	.get('rest/odjava')
			.then(response => this.$router.push("/"));
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