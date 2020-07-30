import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '../components/login-component.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';

class MainViewNotLogged extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-horizontal-layout style="width: 100%; height: 100%;" id="mainLayout">
 <vaadin-vertical-layout style="flex-shrink: 0; flex: 1; flex-grow: 0;">
  <login-component id="loginComponent" style="flex: 0; height: 100%; "></login-component>
  <vaadin-button id="registrationButton" style="flex: 1; flex-shrink: 0; flex-grow: 0; align-self: stretch; color: green; ">
    Registration 
  </vaadin-button>
  <vaadin-button id="forgotPasswordButton" style="align-self: stretch;">
    Forgot password 
  </vaadin-button>
 </vaadin-vertical-layout>
 <vaadin-vertical-layout id="recipeLayout" style="flex-grow: 0; flex-shrink: 1; width: 100%; background-color: var(--lumo-shade-20pct);"></vaadin-vertical-layout>
</vaadin-horizontal-layout>
`;
    }

    static get is() {
        return 'main-view-not-logged';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(MainViewNotLogged.is, MainViewNotLogged);
