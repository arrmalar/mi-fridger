import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-area.js';

class TestTest extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-vertical-layout style="width: 100%; height: 100%;">
 <vaadin-horizontal-layout class="header" style="width: 100%; flex-basis: calc(6*var(--lumo-size-s)); flex-shrink: 0; background-color: var(--lumo-contrast-10pct);"></vaadin-horizontal-layout>
 <vaadin-vertical-layout style="width: 100%; flex-basis: calc(2*var(--lumo-size-s)); flex-shrink: 0; background-color: var(--lumo-contrast-10pct);">
  <label style="align-self: center;">Label</label>
 </vaadin-vertical-layout>
 <vaadin-horizontal-layout style="width: 100%; flex-grow: 1; flex-shrink: 1; flex-basis: auto;">
  <vaadin-vertical-layout class="sidebar" style="flex-basis: calc(8*var(--lumo-size-s)); flex-shrink: 0; background-color: var(--lumo-contrast-5pct);"></vaadin-vertical-layout>
  <vaadin-vertical-layout class="content" style="flex-grow: 1; flex-shrink: 1; flex-basis: auto;">
   <vaadin-text-area label="Write a description" placeholder="Add detailed explanation" style="align-self: stretch; height: 100%;"></vaadin-text-area>
  </vaadin-vertical-layout>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout class="footer" style="width: 100%; flex-basis: var(--lumo-size-xl); flex-shrink: 0; background-color: var(--lumo-contrast-10pct);"></vaadin-horizontal-layout>
</vaadin-vertical-layout>
`;
    }

    static get is() {
        return 'test-test';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(TestTest.is, TestTest);
