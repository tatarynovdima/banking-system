import {createRouter, createWebHistory} from 'vue-router';
import LoginViews from '@/pages/LoginPage/LoginPage.vue';
import RegistrationViews from '@/pages/RegistrationPage/RegistrationPage.vue';
import EmailConfirmation from '@/pages/EmailConfirmationPage/EmailConfirmationPage.vue';
import ConfirmAccount from '@/pages/ConfirmAccountPage/ConfirmAccountPage.vue';
import Main from '@/pages/MainPage/MainViews.vue';
import Replenishment from '@/pages/ReplenishmentPage/ReplenishmentPage.vue';
import Transaction from '@/pages/TransactionPage/TransactionPage.vue';
import Info from '@/pages/InfoPage/InfoPage.vue';
import Transfer from '@/pages/TransferPage/TransferPage.vue';
import Withdraw from '@/pages/WithdrawPage/WithdrawPage.vue';
import Account from '@/pages/AccountPage/AccountPage.vue';
import About from '@/pages/AboutPage/AboutPage.vue';
import Contacts from '@/pages/ContactPage/ContactPage.vue';
import TransferSuccessful from '@/pages/TransferSuccessfulPage/TransferSuccessfulPage.vue';
import ReplenishmentSuccessful from '@/pages/ReplenishmentSuccessfulPage/ReplenishmentSuccessfulPage.vue';



const routes = [
  {
    path: '/main',
    name: 'Main',
    component: Main,
    meta: { requiresAuth: true }
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginViews
  },
  {
    path: '/registration',
    name: 'Registration',
    component: RegistrationViews
  },
  {
    path: '/email-confirmation',
    name: 'EmailConfirmation',
    component: EmailConfirmation
  },
  {
    path: '/confirm-account',
    name: 'ConfirmAccount',
    component: ConfirmAccount
  },
  {
    path: '/replenishment',
    name: 'Replenishment',
    component: Replenishment,
    meta: { requiresAuth: true }
  },
  {
    path: '/transaction',
    name: 'Transaction',
    component: Transaction,
    meta: { requiresAuth: true }
  },
  {
    path: '/info',
    name: 'Info',
    component: Info,
    meta: { requiresAuth: true }
  },
  {
    path: '/transfer',
    name: 'Transfer',
    component: Transfer,
    meta: { requiresAuth: true }
  },
  {
    path: '/withdraw',
    name: 'Withdraw',
    component: Withdraw,
    meta: { requiresAuth: true }
  },
  {
    path: '/account',
    name: 'Account',
    component: Account,
    meta: { requiresAuth: true }
  },
  {
    path: '/about',
    name: 'About',
    component: About,
    meta: { requiresAuth: true }
  },
  {
    path: '/contacts',
    name: 'Contacts',
    component: Contacts,
    meta: { requiresAuth: true }
  },
  {
    path: '/transfer-successful',
    name: 'TransferSuccessful',
    component: TransferSuccessful,
    meta: { requiresAuth: true }
  },
  {
    path: '/replenishment-successful',
    name: 'ReplenishmentSuccessful',
    component: ReplenishmentSuccessful,
    meta: { requiresAuth: true }
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
  const isAuthenticated = localStorage.getItem('token') !== null;

  // Если пользователь пытается перейти на защищенную страницу и не аутентифицирован
  if (requiresAuth && !isAuthenticated) {
    next('/login'); // Перенаправление на страницу входа
  } else if (!to.matched.length) {
    // Если маршрут не существует (то есть matched.length === 0)
    next('/login'); // Перенаправление на страницу входа
  } else if (to.path === '/login' && isAuthenticated) {
    next('/main'); // Если пользователь уже авторизован, перенаправляем его на главную страницу
  } else {
    next(); // Продолжаем навигацию
  }
});


export default router;
