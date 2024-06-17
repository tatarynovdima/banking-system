export default {
    name: 'LoginForm',
    data() {
        return {
            email: '',
            password: '',
            errorMsg: '',
            showPassword: false
        };
    },
    computed: {
        passwordFieldType() {
            return this.showPassword ? 'text' : 'password';
        },
        passwordIconClass() {
            return this.showPassword ? 'fa-eye' : 'fa-eye-slash';
        }
    },
    methods: {
        handleSubmit() {
            fetch("http://localhost:8080/api/login", {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    email: this.email,
                    password: this.password
                })
            })
                .then(response => {
                    if (!response.ok) {
                        return response.text().then(text => {
                            let error = 'Account does not exist';
                            try {
                                const json = JSON.parse(text);
                                error = json.message || error;
                            } catch (e) {
                            }
                            throw new Error(error);
                        });
                    }
                    return response.json();
                })
                .then(data => {
                    // Сохранение токена в localStorage
                    localStorage.setItem('token', data.token);
                    console.log(data.token);
                    this.$router.push('/main');
                })
                .catch((error) => {
                    this.errorMsg = error.message;
                });
        },
        togglePasswordVisibility() {
            this.showPassword = !this.showPassword;
        },
    },
}